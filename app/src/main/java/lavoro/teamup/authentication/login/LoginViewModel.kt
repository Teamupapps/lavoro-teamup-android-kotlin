package lavoro.teamup.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lavoro.teamup.R
import lavoro.teamup.authentication.AuthenticationViewEvent
import lavoro.teamup.authentication.BaseAuthenticationViewModel
import lavoro.teamup.core.SIGN_IN_REQUEST_CODE
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.core.wrapper.UIResource
import lavoro.teamup.data.model.LoginResult
import lavoro.teamup.data.model.user.User
import lavoro.teamup.data.repository.LoginRepository
import lavoro.teamup.data.repository.UserRepository

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : BaseAuthenticationViewModel() {

    internal val googleAuthAttempt = MutableLiveData<Unit>()
    internal val updateLoginButtonAttempt = MutableLiveData<UIResource>()

    private val userState = MutableLiveData<User?>()
    val user: LiveData<User?> get() = userState

    private val signState = MutableLiveData<Boolean>()
    val signed: LiveData<Boolean> get() = signState

    override fun handleEvent(event: AuthenticationViewEvent<LoginResult>) {
        when (event) {
            is AuthenticationViewEvent.GetAuthUser -> {
                getAuthenticatedUser()
            }

            is AuthenticationViewEvent.OnAuthBtnClick -> googleAuthAttempt.value = Unit
            is AuthenticationViewEvent.OnGoogleSignInResult -> onSignInResult(event.result)
            is AuthenticationViewEvent.OnLoginBtnClick -> login(event.email, event.pass)
            else -> {}
        }
    }


    private fun getAuthenticatedUser() = viewModelScope.launch {
        showLoading()
        when (val result = loginRepository.getAuthenticationUser()) {
            is Result.Error -> {
                showError(R.string.cannot_update_remote_data)
                showSignedOutState()
            }

            is Result.Value -> {
                userState.value = result.value

                if (userState.value == null) {
                    showSignedOutState()
                } else {
                    createUserRemoteCredentials(user = userState.value!!)
                    showSignedInState()
                }

            }
        }
        hideLoading()
    }


    private fun createUserRemoteCredentials(user: User) = viewModelScope.launch {

        if (user.isValidUserCredentials() == false) {
            showError(R.string.invalid_credentials)
            return@launch
        }

        showLoading()
        when (val result = userRepository.createUserRemoteCredentials(user = user)) {
            is Result.Error -> result.error.message.actionExceptionMsg(
                error = {
                    showError(R.string.cannot_update_remote_data)
                })

            is Result.Value -> if (result.value) {
                updateLocalUser(user)
            }

        }
        hideLoading()
    }


    private fun updateLocalUser(user: User) = viewModelScope.launch {
        when (userRepository.updateLocalUser(user)) {
            is Result.Error -> showError(R.string.cannot_update_local_entries)
            is Result.Value -> authUpdated()
        }
    }

    private fun onSignInResult(result: LoginResult) = viewModelScope.launch {
        showLoading()
        if (result.requestCode != SIGN_IN_REQUEST_CODE || result.userToken == null) {
            showError(R.string.unable_to_sign_in)
            return@launch
        }

        val createGoogleUserResult = loginRepository.signInGoogleUser(result.userToken)
        if (createGoogleUserResult is Result.Value) {
            getAuthenticatedUser()
        } else showError(R.string.unable_to_sign_in)
        hideLoading()
    }

    private fun login(email: String, pass: String) = viewModelScope.launch {

        if (validEmail(email) == false) return@launch

        if (validPassword(pass) == false) return@launch

        showLoading()

        when (val result = loginRepository.signInWithEmailAndPass(email, pass)) {
            is Result.Error -> result.error.message.actionExceptionMsg(error = {
                showError(R.string.unable_to_sign_in)
            })

            is Result.Value -> getAuthenticatedUser()
        }
        hideLoading()
    }

    private fun User?.isValidUserCredentials(): Boolean = this?.uid?.isNotBlank() == true &&
            this.email.isNotBlank()

    private fun showSignedInState() {
        signState.value = false
        updateLoginButtonAttempt.value = UIResource.StringResource(R.string.signed_in_success)
    }

    private fun showSignedOutState() {
        signState.value = true
        updateLoginButtonAttempt.value = UIResource.StringResource(R.string.login)
    }
}