package lavoro.teamup.data.preference.transaction

import android.content.Context
import lavoro.teamup.data.preference.BasePreferenceProvider


private const val TRANS_FILTER_SELL = "TRANS_FILTER_SELL"

class TransactionFilterPreferenceImpl(
    context: Context
) : BasePreferenceProvider(context), TransactionFilterPreference {

    override fun filterListBySellOnly(): Boolean =
        preferences.getBoolean(TRANS_FILTER_SELL, false)

}