package lavoro.teamup.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lavoro.teamup.R
import lavoro.teamup.core.base.BaseViewModel
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.core.wrapper.UIResource
import lavoro.teamup.data.model.user.User
import lavoro.teamup.data.repository.UserRepository

class HomeActivityViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<HomeActivityEvent>() {

    private val userState = MutableLiveData<User?>()

    internal val loginAttempt = MutableLiveData<Unit>()
    internal val requestPermissionsAttempt = MutableLiveData<Unit>()
    internal val updateActionToolbarAttempt = MutableLiveData<UIResource>()
    internal val updateActionToolbarColorAttempt = MutableLiveData<Boolean>()

    override fun handleEvent(event: HomeActivityEvent) {
        when (event) {
            is HomeActivityEvent.OnStartGetUser -> getUser(
                resultAction = {
                    if (userState.value == null) {
                        updateToolbarActionTitle(R.string.cannot_find_auth_user, isError = true)
                        checkIfAuthorizedRequired()
                    } else {
                        updateToolbarActionTitle(R.string.read_remote_user)
                        checkIfRemoteUserActivated()
                    }

                })
        }
    }

    private fun getUser(resultAction: (() -> Unit)) = viewModelScope.launch {
        when (val result = userRepository.getLocalUser()) {
            is Result.Error -> {
                showUnauthorised()
                moveToLoginView()
            }

            is Result.Value -> {
                userState.value = result.value
                resultAction.invoke()
            }
        }
    }

    private fun checkIfAuthorizedRequired() = viewModelScope.launch {
        when (val result = userRepository.checkIfAuthorizedRequired()) {
            is Result.Error -> Unit

            is Result.Value -> if (result.value) moveToLoginView()
            else updateToolbarActionTitle(resId = R.string.unauthorized_summary, isError = true)
        }
    }

    private fun checkIfRemoteUserActivated() = viewModelScope.launch {
        showLoading()
        when (val result = userRepository.getRemoteUser()) {
            is Result.Error -> result.error.message.actionExceptionMsg(offline = {
                updateToolbarActionTitle(R.string.no_internet_connect, isError = true)
            }, unauthorised = {
                updateToolbarActionTitle(R.string.unauthorized, isError = true)
            }, error = {
                updateToolbarActionTitle(resId = R.string.cannot_read_remote_data, isError = true)
                moveToLoginView()
            })

            is Result.Value -> result.value.let { remoteUser ->

                if (remoteUser == null) {
                    updateToolbarActionTitle(
                        resId = R.string.cannot_read_remote_data,
                        isError = true
                    )
                    moveToLoginView()
                    return@launch
                }

                if (remoteUser == userState.value) {
                    updateToolbarActionTitle(
                        resId = if (remoteUser.activated) R.string.sync_success else R.string.deactivated,
                        isError = remoteUser.activated == false
                    )
                } else {
                    updateToolbarActionTitle(R.string.sync_setup)
                    checkIfUpdateLocalRequired(newUser = remoteUser)
                }

                requestPermissions()
            }
        }
        hideLoading()
    }

    private fun checkIfUpdateLocalRequired(newUser: User) = viewModelScope.launch {
        if (userRepository.updateLocalUser(user = newUser) is Result.Value) updateToolbarActionTitle(
            resId = R.string.sync_success
        )
        else updateToolbarActionTitle(R.string.sync_error, isError = true)
    }

    private fun updateToolbarActionTitle(resId: Int, isError: Boolean = false) {
        updateActionToolbarAttempt.value = UIResource.StringResource(resId)
        updateActionToolbarColorAttempt.value = isError
    }

    private fun requestPermissions() {
        requestPermissionsAttempt.value = Unit
    }

    private fun moveToLoginView() {
        loginAttempt.value = Unit
    }
}