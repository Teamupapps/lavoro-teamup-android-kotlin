package lavoro.teamup.preferencesettings.buildlogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lavoro.teamup.R
import lavoro.teamup.core.base.BaseViewModel
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.core.wrapper.UIResource
import lavoro.teamup.data.reminder.ReminderAPI
import lavoro.teamup.data.repository.preferences.PreferenceRepository

class PreferenceViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val reminderAPI: ReminderAPI
) : BaseViewModel<PreferenceEvent>() {

    private val cacheUpdateState = MutableLiveData<Unit>()
    val cacheUpdated: LiveData<Unit> get() = cacheUpdateState

    private val backupTextState = MutableLiveData<UIResource>()
    val backupText: LiveData<UIResource> get() = backupTextState

    private val dbClearState = MutableLiveData<Unit>()
    val dbCleared: LiveData<Unit> get() = dbClearState

    override fun handleEvent(event: PreferenceEvent) {
        when (event) {
            PreferenceEvent.OnClearCacheClick -> {
                clearPrefLastCacheTimes()
                clearTeamDatabase()
            }

            PreferenceEvent.OnResetCacheClick -> {
                resetInputCacheTimes()
            }

            is PreferenceEvent.OnServerSwitchUpdate -> {
                updateRemoteServerUse(event.enable)
                if (event.enable) clearPrefLastCacheTimes()
            }

            is PreferenceEvent.OnBackupTransactionSwitchUpdate -> {
                updateTransactionAutoBackupUse(event.enable)
                updateBackupReminder(enable = event.enable)
            }
        }
    }

    private fun resetInputCacheTimes() = viewModelScope.launch {
        if (preferenceRepository.resetInputCacheTimes() is Result.Value) cacheUpdateState.value =
            Unit
        else showError(R.string.cannot_update_local_entries)
    }

    private fun clearPrefLastCacheTimes() = viewModelScope.launch {
        if (preferenceRepository.clearLastCacheTimes() is Result.Value) cacheUpdateState.value =
            Unit
        else showError(R.string.cannot_update_local_entries)
    }

    private fun clearTeamDatabase() = viewModelScope.launch {
        if (preferenceRepository.clearAppDatabase() is Result.Value) dbClearState.value = Unit
        else showError(R.string.cannot_update_local_entries)
    }

    private fun updateRemoteServerUse(isEnable: Boolean) = viewModelScope.launch {
        if (preferenceRepository.updateRemoteServerUse(isEnable) is Result.Error) showError(R.string.cannot_update_local_entries)
    }

    private fun updateTransactionAutoBackupUse(enable: Boolean) = viewModelScope.launch {
        if (preferenceRepository.updateAutoBackupsUse(enable) is Result.Error) showError(R.string.cannot_update_local_entries)
    }

    private fun updateBackupTitle(msgRes: Int) {
        backupTextState.value = UIResource.StringResource(msgRes)
    }

    private fun updateBackupReminder(enable: Boolean) {
        when (enable) {
            true -> if (reminderAPI.setupReminderAlarmForBackup() is Result.Value) updateBackupTitle(
                R.string.backups_setup
            )
            else showError(R.string.backups_alarm_error)

            false -> if (reminderAPI.cancelBackupReminder() is Result.Value) updateBackupTitle(R.string.backups_canceled)
            else showError(R.string.backups_alarm_error)
        }
    }

}