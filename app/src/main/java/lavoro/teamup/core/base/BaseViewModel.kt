package lavoro.teamup.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lavoro.teamup.R
import lavoro.teamup.core.DEACTIVATED
import lavoro.teamup.core.NO_INTERNET_CONNECTION
import lavoro.teamup.core.UNAUTHORIZED
import lavoro.teamup.core.wrapper.UIResource

abstract class BaseViewModel<T> : ViewModel() {
    abstract fun handleEvent(event: T)

    private val loadingState = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = loadingState

    private val errorState = MutableLiveData<UIResource>()
    val error: LiveData<UIResource> get() = errorState

    protected fun showLoading() {
        loadingState.value = true
    }

    protected fun hideLoading() {
        loadingState.value = false
    }

    protected fun showError(msgRes: Int) {
        errorState.value = UIResource.StringResource(msgRes)
    }

    protected fun String?.actionExceptionMsg(
        offline: (() -> Unit)? = { showNoConnection() },
        deactivated: (() -> Unit)? = { showDeactivated() },
        unauthorised: (() -> Unit)? = { showUnauthorised() },
        error: (() -> Unit)? = null
    ) {
        when (this) {
            DEACTIVATED -> deactivated?.invoke()
            UNAUTHORIZED -> unauthorised?.invoke()
            NO_INTERNET_CONNECTION -> offline?.invoke()
            else -> error?.invoke()
        }
    }

    protected fun showUnauthorised() {
        errorState.value = UIResource.StringResource(R.string.unauthorized)
    }

    private fun showDeactivated() {
        errorState.value = UIResource.StringResource(R.string.deactivated)
    }

    private fun showNoConnection() {
        errorState.value = UIResource.StringResource(R.string.no_internet_connect)
    }
}
