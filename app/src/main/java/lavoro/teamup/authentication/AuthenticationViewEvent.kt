package lavoro.teamup.authentication

sealed class AuthenticationViewEvent<out T> {
    data object GetAuthUser : AuthenticationViewEvent<Nothing>()
    data object OnAuthBtnClick : AuthenticationViewEvent<Nothing>()
    data class OnLoginBtnClick(val email: String, val pass: String) : AuthenticationViewEvent<Nothing>()
    data class OnGoogleSignInResult<out LoginResult>(val result: LoginResult) :
        AuthenticationViewEvent<LoginResult>()
    data class OnSignupBtnClick(val email: String, val pass: String) : AuthenticationViewEvent<Nothing>()
}