package lavoro.teamup.citylist.buildlogic

import android.app.Application
import lavoro.teamup.core.base.BaseViewInjector

class CityViewInjector(
    app: Application
) : BaseViewInjector(app) {
    fun provideViewModelFactory() = CityViewModelFactory(
        getCityRepository()
    )
}