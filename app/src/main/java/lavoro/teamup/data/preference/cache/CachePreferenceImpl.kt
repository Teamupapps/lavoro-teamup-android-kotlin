package lavoro.teamup.data.preference.cache

import android.content.Context
import lavoro.teamup.data.preference.BasePreferenceCacheProvider

class CachePreferenceImpl(
    context: Context
) : BasePreferenceCacheProvider(context), CachePreference {

    override fun clearLastCacheTimes() = actionClearLastCacheTimes()

    override fun resetInputCacheTimes() = actionResetInputCacheTimes()

}