package lavoro.teamup.data.repository.preferences

import lavoro.teamup.core.wrapper.Result

interface PreferenceRepository : UtilPreferenceRepository, AdvancedPreferenceRepository,
    TransactionPreferenceRepository {
    suspend fun updateRemoteServerUse(enable: Boolean): Result<Exception, Unit>
    suspend fun updateAutoBackupsUse(enable: Boolean): Result<Exception, Unit>
    suspend fun resetInputCacheTimes(): Result<Exception, Unit>
    suspend fun clearLastCacheTimes(): Result<Exception, Unit>
    suspend fun clearAppDatabase(): Result<Exception, Unit>
}
