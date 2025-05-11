package lavoro.teamup.companyprofile.buildlogic

import android.app.Application
import lavoro.teamup.core.base.BaseViewInjector

class CompanyProfileViewInjector(
    app: Application
) : BaseViewInjector(app) {
    fun provideViewModelFactory() = CompanyProfileViewModelFactory()
}
