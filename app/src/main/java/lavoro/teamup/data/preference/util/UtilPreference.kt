package lavoro.teamup.data.preference.util

interface UtilPreference : BalancePreference, StatisticsPreference, NotePreference, StockPreference,
    TransactionPreference {
    fun isRemoteServerUsed(): Boolean
    fun updateRemoteServerUse(enable: Boolean)
    fun updateAutoBackupsUse(enable: Boolean)

}