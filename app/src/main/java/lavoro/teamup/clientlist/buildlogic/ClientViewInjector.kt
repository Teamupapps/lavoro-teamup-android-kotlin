package lavoro.teamup.clientlist.buildlogic

import android.app.Application
import lavoro.teamup.core.base.BaseViewInjector

class ClientViewInjector(
    app: Application
) : BaseViewInjector(app) {

    fun provideViewModelFactory() = ClientViewModelFactory(
        getClientRepository(),
        getCityRepository(),
        getPreferenceRepository()
    )
}