package lavoro.teamup.data.preference.transaction

import android.content.Context
import lavoro.teamup.data.preference.BasePreferenceProvider

private const val USE_CLIENT_ENTRY = "USE_CLIENT_ENTRY"
private const val USE_PRODUCT_ENTRY = "USE_PRODUCT_ENTRY"

class TransactionPreferenceImpl(
    context: Context
) : BasePreferenceProvider(context), TransactionPreference {

    override fun useClientEntry(): Boolean =
        preferences.getBoolean(USE_CLIENT_ENTRY, false)

    override fun useProductEntry(): Boolean =
        preferences.getBoolean(USE_PRODUCT_ENTRY, false)

}