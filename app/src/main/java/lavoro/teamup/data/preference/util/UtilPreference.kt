package lavoro.teamup.data.preference.util

interface UtilPreference : BalancePreference, StaticsPreference, NotePreference, StockPreference {
    fun isRemoteServerUsed(): Boolean
    fun updateRemoteServerUse(enable: Boolean)
    fun updateAutoBackupsUse(enable: Boolean)

}