package lavoro.teamup.productlist.buildlogic

import android.app.Application
import lavoro.teamup.core.base.BaseViewInjector

class ProductViewInjector(
    app: Application
) : BaseViewInjector(app) {
    fun provideViewModelFactory() = ProductViewModelFactory(
        getProductRepository(), getBrandRepository(), getPreferenceRepository()
    )
}