package lavoro.teamup.authentication

import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import lavoro.teamup.R
import lavoro.teamup.core.view.MAX_PASS_DIG
import lavoro.teamup.core.view.MIN_PASS_DIG
import lavoro.teamup.core.view.beginEmailLayoutWatcher
import lavoro.teamup.core.view.beginTitleLengthLayoutWatcher
import lavoro.teamup.core.view.setEmailInput
import lavoro.teamup.core.view.setPasswordInput


open class BaseAuthenticationView : Fragment() {

    protected fun EditText.setUserEmailInput(inputLayout: TextInputLayout) = try {
        this.apply {
            setEmailInput()

            inputLayout.hint = getString(R.string.email)

            beginEmailLayoutWatcher(
                inputLayout = inputLayout,
                eMsg = getString(R.string.invalid)
            )
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    protected fun EditText.setUserPasswordInput(inputLayout: TextInputLayout) = try {
        this.apply {
            setPasswordInput()

            inputLayout.hint = getString(R.string.password)
            inputLayout.isPasswordVisibilityToggleEnabled = true

            beginTitleLengthLayoutWatcher(
                inputLayout = inputLayout,
                errorMsg = getString(R.string.invalid),
                minDig = MIN_PASS_DIG,
                maxDig = MAX_PASS_DIG
            )
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }


}