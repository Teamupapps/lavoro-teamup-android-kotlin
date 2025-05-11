package lavoro.teamup.data.repository

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.user.User

interface UserRepository {
    suspend fun getLocalUser(): Result<Exception, User?>
    suspend fun getRemoteUser(): Result<Exception, User?>
    suspend fun checkIfAuthorizedRequired(): Result<Exception, Boolean>
    suspend fun updateLocalUser(user: User): Result<Exception, Unit>
    suspend fun createUserRemoteCredentials(user: User): Result<Exception, Boolean>
    suspend fun updateRemoteUser(name: String): Result<Exception, Unit>
    suspend fun signOutCurrentUser(): Result<Exception, Unit>

}