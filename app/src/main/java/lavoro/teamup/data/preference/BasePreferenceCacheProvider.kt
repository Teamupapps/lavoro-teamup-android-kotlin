package lavoro.teamup.data.preference

import android.content.Context
import lavoro.teamup.core.BRAND_CACHE_TIME
import lavoro.teamup.core.CITY_CACHE_TIME
import lavoro.teamup.core.CLIENT_CACHE_TIME
import lavoro.teamup.core.NOTE_CACHE_TIME
import lavoro.teamup.core.PRODUCT_CACHE_TIME
import lavoro.teamup.core.TRANSACTION_CACHE_TIME

open class BasePreferenceCacheProvider(context: Context) :
    BasePreferenceProvider(context) {

    protected val cityLastCache = "city_last_cache"
    protected val clientLastCache = "client_last_cache"
    protected val brandLastCache = "brand_last_cache"
    protected val productLastCache = "product_last_cache"
    protected val noteLastCache = "note_last_cache"
    protected val transactionLastCache = "transaction_last_cache"

    protected fun actionClearLastCacheTimes() {
        preferenceEditor.let {
            it.putString(cityLastCache, null)
            it.putString(clientLastCache, null)
            it.putString(brandLastCache, null)
            it.putString(productLastCache, null)
            it.putString(transactionLastCache, null)
            it.putString(noteLastCache, null)
            it.apply()
        }
    }

    protected fun actionResetInputCacheTimes() {
        preferenceEditor.let {
            it.putString(CITY_CACHE_TIME, "4")
            it.putString(CLIENT_CACHE_TIME, "4")
            it.putString(BRAND_CACHE_TIME, "4")
            it.putString(PRODUCT_CACHE_TIME, "4")
            it.putString(TRANSACTION_CACHE_TIME, "10")
            it.putString(NOTE_CACHE_TIME, "10")
            it.apply()
        }
    }
}