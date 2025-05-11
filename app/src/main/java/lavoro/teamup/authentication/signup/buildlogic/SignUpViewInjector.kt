package lavoro.teamup.authentication.signup.buildlogic

import android.app.Application
import lavoro.teamup.authentication.BaseAuthenticationViewInjector

class SignUpViewInjector(
    app: Application
) : BaseAuthenticationViewInjector(app) {

    fun provideViewModelFactory() = SignUpViewModelFactory(
        getLoginRepository()
    )
}
