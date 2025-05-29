package lavoro.teamup.core.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import lavoro.teamup.data.connectivity.ConnectivityInterceptorImpl
import lavoro.teamup.data.implementation.BrandRepositoryImpl
import lavoro.teamup.data.implementation.CityRepositoryImpl
import lavoro.teamup.data.implementation.ClientRepositoryImpl
import lavoro.teamup.data.implementation.NoteRepositoryImpl
import lavoro.teamup.data.implementation.PreferenceRepositoryImpl
import lavoro.teamup.data.implementation.ProductRepositoryImpl
import lavoro.teamup.data.implementation.TeamRepositoryImpl
import lavoro.teamup.data.implementation.UserRepositoryImpl
import lavoro.teamup.data.preference.advanced.AdvancedPreferenceImpl
import lavoro.teamup.data.preference.cache.CachePreferenceImpl
import lavoro.teamup.data.preference.datalist.BrandPreferenceImpl
import lavoro.teamup.data.preference.datalist.CityPreferenceImpl
import lavoro.teamup.data.preference.datalist.ClientPreferenceImpl
import lavoro.teamup.data.preference.datalist.NotePreferenceImpl
import lavoro.teamup.data.preference.datalist.ProductPreferenceImpl
import lavoro.teamup.data.preference.util.UtilPreferenceImpl
import lavoro.teamup.data.room.TeamDatabase

open class BaseViewInjector(
    val app: Application
) : AndroidViewModel(app) {

    protected fun userDao() = TeamDatabase.invoke(getApplication()).userDao()

    private fun cityDao() = TeamDatabase.invoke(getApplication()).cityDao()
    private fun clientDao() = TeamDatabase.invoke(getApplication()).clientDao()
    private fun brandDao() = TeamDatabase.invoke(getApplication()).brandDao()
    private fun productDao() = TeamDatabase.invoke(getApplication()).productDao()
    private fun noteDao() = TeamDatabase.invoke(getApplication()).noteDao()

    protected fun utilPreference() = UtilPreferenceImpl(getApplication())
    protected fun cachePreference() = CachePreferenceImpl(getApplication())
    protected fun advancedPreference() = AdvancedPreferenceImpl(getApplication())

    private fun cityPreference() = CityPreferenceImpl(getApplication())
    private fun clientPreference() = ClientPreferenceImpl(getApplication())
    private fun brandPreference() = BrandPreferenceImpl(getApplication())
    private fun productPreference() = ProductPreferenceImpl(getApplication())
    private fun notePreference() = NotePreferenceImpl(getApplication())

    protected fun connectInterceptor() = ConnectivityInterceptorImpl(getApplication())

    protected fun getCityRepository() = CityRepositoryImpl(
        local = cityDao(),
        pref = cityPreference(),
        localUser = userDao(),
        utilPreference = utilPreference(),
        connectInterceptor = connectInterceptor()
    )

    protected fun getClientRepository() = ClientRepositoryImpl(
        local = clientDao(),
        pref = clientPreference(),
        localUser = userDao(),
        utilPreference = utilPreference(),
        connectInterceptor = connectInterceptor()
    )

    protected fun getBrandRepository() = BrandRepositoryImpl(
        local = brandDao(),
        pref = brandPreference(),
        localUser = userDao(),
        utilPreference = utilPreference(),
        connectInterceptor = connectInterceptor()
    )

    protected fun getProductRepository() = ProductRepositoryImpl(
        local = productDao(),
        pref = productPreference(),
        localUser = userDao(),
        utilPreference = utilPreference(),
        connectInterceptor = connectInterceptor()
    )

    protected fun getNoteRepository() = NoteRepositoryImpl(
        local = noteDao(),
        preference = notePreference(),
        localUser = userDao(),
        utilPreference = utilPreference(),
        connectInterceptor = connectInterceptor()
    )

    protected fun getUserRepository() = UserRepositoryImpl(
        local = userDao(),
        utilPreference = utilPreference(),
        connectInterceptor = connectInterceptor()
    )

    protected fun getTeamRepository() = TeamRepositoryImpl(
        localUser = userDao(),
        utilPreference = utilPreference(),
        connectInterceptor = connectInterceptor()
    )

    protected fun getPreferenceRepository() = PreferenceRepositoryImpl(
        localDB = TeamDatabase.invoke(getApplication()),
        utilPreference = utilPreference(),
        cachePreference = cachePreference(),
        advancedPreference = advancedPreference(),
    )

}