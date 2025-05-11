package lavoro.teamup.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lavoro.teamup.core.base.BaseViewModel
import lavoro.teamup.core.view.MAX_PASS_DIG
import lavoro.teamup.core.view.MIN_PASS_DIG
import lavoro.teamup.core.view.isEmailAddress
import lavoro.teamup.data.model.LoginResult

abstract class BaseAuthenticationViewModel :
    BaseViewModel<AuthenticationViewEvent<LoginResult>>() {

    internal val emailValidateState = MutableLiveData<Boolean>()
    internal val passwordValidateState = MutableLiveData<Boolean>()

    private val updateState = MutableLiveData<Unit>()
    val updated: LiveData<Unit> get() = updateState

    protected fun authUpdated() {
        updateState.value = Unit
    }

    protected fun validEmail(email: String) = if (email.isEmailAddress()) {
        emailValidateState.value = true
        true
    } else {
        emailValidateState.value = false
        false
    }

    protected fun validPassword(password: String) =
        if (password.length in MIN_PASS_DIG..MAX_PASS_DIG) {
            passwordValidateState.value = true
            true
        } else {
            passwordValidateState.value = false
            false
        }
}