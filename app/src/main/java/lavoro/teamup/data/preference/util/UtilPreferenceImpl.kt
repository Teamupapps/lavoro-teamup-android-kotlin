package lavoro.teamup.data.preference.util

import android.content.Context
import lavoro.teamup.core.USE_BACKUPS
import lavoro.teamup.core.USE_REMOTE_SERVER
import lavoro.teamup.data.preference.BasePreferenceProvider

private const val USE_NOTES = "USE_NOTES"
private const val USE_STATISTICS = "USE_STATISTICS"
private const val USE_BALANCE = "USE_BALANCE"
private const val USE_STOCK = "USE_STOCK"

private const val STOCK_TODAY_ONLY = "STOCK_TODAY_ONLY"
private const val STATISTICS_TODAY_ONLY = "STATISTICS_TODAY_ONLY"
private const val BALANCE_TODAY_ONLY = "BALANCE_TODAY_ONLY"
private const val TRANSACTION_TODAY_ONLY = "TRANSACTION_TODAY_ONLY"
private const val NOTES_TODAY_ONLY = "NOTES_TODAY_ONLY"

private const val USE_CLIENT_ENTRY = "USE_CLIENT_ENTRY"
private const val USE_PRODUCT_ENTRY = "USE_PRODUCT_ENTRY"

class UtilPreferenceImpl(
    context: Context
) : BasePreferenceProvider(context), UtilPreference {

    override fun isRemoteServerUsed(): Boolean = preferences.getBoolean(USE_REMOTE_SERVER, false)

    override fun updateRemoteServerUse(enable: Boolean) =
        preferenceEditor.putBoolean(USE_REMOTE_SERVER, enable).apply()

    override fun updateAutoBackupsUse(enable: Boolean) =
        preferenceEditor.putBoolean(USE_BACKUPS, enable).apply()

    override fun isBalanceUsed(): Boolean = preferences.getBoolean(USE_BALANCE, true)

    override fun isStatisticsUsed(): Boolean = preferences.getBoolean(USE_STATISTICS, true)

    override fun isNoteUsed(): Boolean = preferences.getBoolean(USE_NOTES, true)

    override fun isStockUsed(): Boolean = preferences.getBoolean(USE_STOCK, true)

    override fun calculateTodayStatisticsOnly(): Boolean =
        preferences.getBoolean(STATISTICS_TODAY_ONLY, true)


    override fun calculateTodayStockOnly(): Boolean = preferences.getBoolean(STOCK_TODAY_ONLY, true)

    override fun calculateTodayBalanceOnly(): Boolean =
        preferences.getBoolean(BALANCE_TODAY_ONLY, true)

    override fun displayTodayNotesOnly(): Boolean =
        preferences.getBoolean(NOTES_TODAY_ONLY, true)

    override fun displayTodayTransactionsOnly(): Boolean =
        preferences.getBoolean(TRANSACTION_TODAY_ONLY, true)

    override fun useClientEntry(): Boolean = preferences.getBoolean(USE_CLIENT_ENTRY, false)

    override fun useProductEntry(): Boolean = preferences.getBoolean(USE_PRODUCT_ENTRY, false)

}