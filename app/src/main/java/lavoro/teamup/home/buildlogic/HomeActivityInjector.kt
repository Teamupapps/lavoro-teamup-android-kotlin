package lavoro.teamup.home.buildlogic

import android.app.Application
import lavoro.teamup.core.base.BaseViewInjector

class HomeActivityInjector(
    app: Application
) : BaseViewInjector(app) {
    fun provideViewModelFactory() = HomeActivityViewModelFactory(getUserRepository())
}
