package lavoro.teamup.data.preference.datalist

interface BaseListPreference {

    fun updateCacheTimeToNow()

    fun clearListCacheTime(): Boolean

    fun isListUpdateNeeded(): Boolean

    fun isZeroInputCacheTime(): Boolean
}
