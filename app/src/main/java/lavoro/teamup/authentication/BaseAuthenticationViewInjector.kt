package lavoro.teamup.authentication

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import lavoro.teamup.core.base.BaseViewInjector
import lavoro.teamup.data.implementation.LoginRepositoryImpl

open class BaseAuthenticationViewInjector(
    app: Application
) : BaseViewInjector(app) {

    protected fun getLoginRepository() = LoginRepositoryImpl(
        auth = FirebaseAuth.getInstance()
    )
}
