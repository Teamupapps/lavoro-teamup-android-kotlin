package lavoro.teamup.data.preference.util

import android.content.Context
import lavoro.teamup.core.USE_BACKUPS
import lavoro.teamup.core.USE_REMOTE_SERVER
import lavoro.teamup.data.preference.BasePreferenceProvider

private const val USE_NOTES = "USE_NOTES"
private const val USE_STOCK = "USE_STOCK"
private const val USE_STATICS = "USE_STATICS"

private const val USE_BALANCE = "USE_BALANCE"
private const val BALANCE_TODAY_ONLY = "BALANCE_TODAY_ONLY"

class UtilPreferenceImpl(
    context: Context
) : BasePreferenceProvider(context), UtilPreference {

    override fun isRemoteServerUsed(): Boolean = preferences.getBoolean(USE_REMOTE_SERVER, false)

    override fun updateRemoteServerUse(enable: Boolean) =
        preferenceEditor.putBoolean(USE_REMOTE_SERVER, enable).apply()

    override fun updateAutoBackupsUse(enable: Boolean) =
        preferenceEditor.putBoolean(USE_BACKUPS, enable).apply()


    override fun isBalanceUsed(): Boolean = preferences.getBoolean(USE_BALANCE, true)

    override fun isStaticsUsed(): Boolean = preferences.getBoolean(USE_STATICS, true)

    override fun isNoteUsed(): Boolean = preferences.getBoolean(USE_NOTES, true)

    override fun isStockUsed(): Boolean = preferences.getBoolean(USE_STOCK, true)

    override fun calculateTodayBalanceOnly(): Boolean = preferences.getBoolean(BALANCE_TODAY_ONLY, true)

}