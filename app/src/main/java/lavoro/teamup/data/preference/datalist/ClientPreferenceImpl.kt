package lavoro.teamup.data.preference.datalist

import android.content.Context
import lavoro.teamup.core.CLIENT_CACHE_TIME
import lavoro.teamup.data.preference.BasePreferenceCacheProvider
import org.threeten.bp.ZonedDateTime

class ClientPreferenceImpl(
    context: Context
) : BasePreferenceCacheProvider(context), BaseListPreference {

    private fun inputCacheTime() = preferences.getString(CLIENT_CACHE_TIME, "4")?.toInt() ?: 4

    private fun getLastCacheTime() =
        preferences.getString(clientLastCache, null)

    override fun updateCacheTimeToNow() =
        preferenceEditor.putString(clientLastCache, ZonedDateTime.now().toString()).apply()

    override fun clearListCacheTime() =
        preferenceEditor.remove(clientLastCache).commit()

    override fun isListUpdateNeeded(): Boolean {
        if (getLastCacheTime() == null) return true
        return try {
            val timeAgo = ZonedDateTime.now().minusHours(inputCacheTime().toLong())
            val fetchedTime = ZonedDateTime.parse(getLastCacheTime())
            fetchedTime.isBefore(timeAgo)
        } catch (ex: Exception) {
            true
        }
    }

    override fun isZeroInputCacheTime() = inputCacheTime() == 0
}