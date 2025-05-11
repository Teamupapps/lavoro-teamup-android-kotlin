package lavoro.teamup.authentication.signup

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lavoro.teamup.R
import lavoro.teamup.authentication.AuthenticationViewEvent
import lavoro.teamup.authentication.BaseAuthenticationViewModel
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.LoginResult
import lavoro.teamup.data.repository.LoginRepository

class SignUpViewModel(
    private val loginRepository: LoginRepository
) : BaseAuthenticationViewModel() {

    override fun handleEvent(event: AuthenticationViewEvent<LoginResult>) {
        if (event is AuthenticationViewEvent.OnSignupBtnClick) {
            signup(event.email, event.pass)
        }
    }

    private fun signup(email: String, pass: String) = viewModelScope.launch {

        if (validEmail(email) == false) return@launch

        if (validPassword(pass) == false) return@launch

        showLoading()

        when (val result = loginRepository.signUpWithEmailAndPass(email, pass)) {
            is Result.Error -> result.error.message.actionExceptionMsg(
                error = { showError(R.string.unable_to_sign_up) }
            )

            is Result.Value -> authUpdated()
        }
        hideLoading()
    }
}
