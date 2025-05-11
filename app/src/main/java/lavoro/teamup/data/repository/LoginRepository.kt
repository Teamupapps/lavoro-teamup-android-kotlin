package lavoro.teamup.data.repository

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.user.User

interface LoginRepository {
    suspend fun getAuthenticationUser(): Result<Exception, User?>
    suspend fun signInGoogleUser(idToken: String): Result<Exception, Unit>
    suspend fun signUpWithEmailAndPass(email: String, password: String): Result<Exception, Unit>
    suspend fun signInWithEmailAndPass(email: String, password: String): Result<Exception, Unit>
}
