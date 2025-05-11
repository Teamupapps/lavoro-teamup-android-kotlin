package lavoro.teamup.preferencesettings.buildlogic

sealed class PreferenceEvent {
    data object OnClearCacheClick : PreferenceEvent()
    data object OnResetCacheClick : PreferenceEvent()
    data class OnServerSwitchUpdate(val enable: Boolean) : PreferenceEvent()
    data class OnBackupTransactionSwitchUpdate(val enable: Boolean) : PreferenceEvent()
}