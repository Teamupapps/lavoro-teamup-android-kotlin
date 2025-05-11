package lavoro.teamup.data.preference.advanced

import android.content.Context
import lavoro.teamup.data.preference.BasePreferenceProvider

private const val SUGGEST_SHARE = "SUGGEST_SHARE"
private const val SUGGEST_ADD = "SUGGEST_ADD"

class AdvancedPreferenceImpl(
    context: Context
) : BasePreferenceProvider(context), AdvancedPreference {

    override fun isShareSuggestUsed(): Boolean = preferences.getBoolean(SUGGEST_SHARE, false)

    override fun isAddSuggestUsed(): Boolean = preferences.getBoolean(SUGGEST_ADD, true)

}