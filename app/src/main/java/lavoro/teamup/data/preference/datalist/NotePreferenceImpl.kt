package lavoro.teamup.data.preference.datalist

import android.content.Context
import lavoro.teamup.core.NOTE_CACHE_TIME
import lavoro.teamup.data.preference.BasePreferenceCacheProvider
import org.threeten.bp.ZonedDateTime

class NotePreferenceImpl(
    context: Context
) : BasePreferenceCacheProvider(context), BaseListPreference {

    private fun inputCacheTime() = preferences.getString(NOTE_CACHE_TIME, "10")?.toInt() ?: 10

    private fun getLastCacheTime() = preferences.getString(noteLastCache, null)

    override fun clearListCacheTime() = preferenceEditor.remove(noteLastCache).commit()

    override fun updateCacheTimeToNow() =
        preferenceEditor.putString(noteLastCache, ZonedDateTime.now().toString()).apply()

    override fun isListUpdateNeeded(): Boolean {
        if (getLastCacheTime() == null) return true
        return try {
            val timeAgo = ZonedDateTime.now().minusMinutes(inputCacheTime().toLong())
            val fetchedTime = ZonedDateTime.parse(getLastCacheTime())
            fetchedTime.isBefore(timeAgo)
        } catch (ex: Exception) {
            true
        }
    }

    override fun isZeroInputCacheTime() = inputCacheTime() == 0
}