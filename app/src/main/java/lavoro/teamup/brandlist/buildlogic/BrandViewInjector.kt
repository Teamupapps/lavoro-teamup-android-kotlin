package lavoro.teamup.brandlist.buildlogic

import android.app.Application
import lavoro.teamup.core.base.BaseViewInjector

class BrandViewInjector(
    app: Application
) : BaseViewInjector(app) {
    fun provideViewModelFactory() = BrandViewModelFactory(
        getBrandRepository()
    )
}