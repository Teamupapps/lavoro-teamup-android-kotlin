package lavoro.teamup.authentication.login.buildlogic

import android.app.Application
import lavoro.teamup.authentication.BaseAuthenticationViewInjector

class LoginViewInjector(
    app: Application
) : BaseAuthenticationViewInjector(app) {

    fun provideViewModelFactory() = LoginViewModelFactory(
        getLoginRepository(), getUserRepository()
    )
}
