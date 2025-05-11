package lavoro.teamup.core.view

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

internal fun TextInputLayout.checkValidation(validate: Boolean, errorMsg: String?) {
    isErrorEnabled = validate == false
    error = if (validate) null else errorMsg ?: "*"
}

internal fun TextInputLayout.setIconAction(
    icon: Int, action: (() -> Unit)?
) {
    setEndIconActivated(true)
    isEndIconVisible = true
    setEndIconDrawable(icon)
    setEndIconOnClickListener {
        action?.invoke()
    }
}

internal fun EditText.beginEmailLayoutWatcher(
    inputLayout: TextInputLayout, eMsg: String
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable) {
        }

        override fun onTextChanged(
            text: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            text.let {

                if (TextUtils.isEmpty(it)) return

                if (it.toString().isEmailAddress()) {
                    inputLayout.isErrorEnabled = false
                    inputLayout.error = null
                } else {
                    inputLayout.isErrorEnabled = true
                    inputLayout.error = eMsg
                }
            }
        }
    })
}

internal fun EditText.beginTitleLengthLayoutWatcher(
    inputLayout: TextInputLayout,
    errorMsg: String,
    minDig: Int,
    maxDig: Int
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable) {
        }

        override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int): Unit {
            text.let {

                if (TextUtils.isEmpty(it)) return

                if (it.length in minDig..maxDig) {
                    inputLayout.isErrorEnabled = false
                    inputLayout.error = null
                } else {
                    inputLayout.isErrorEnabled = true
                    inputLayout.error = errorMsg
                }
            }
        }
    })
}

internal fun EditText.beginInputLayoutAssetWatcher(
    inputLayout: TextInputLayout,
    errorMsg: String,
    maxDig: Int,
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable) {
        }

        override fun onTextChanged(
            text: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            text.let {

                if (TextUtils.isEmpty(it)) return

                if (it.toString().isValidFormattedNumber(maxDig)) {
                    inputLayout.isErrorEnabled = false
                    inputLayout.error = null
                } else {
                    inputLayout.isErrorEnabled = true
                    inputLayout.error = errorMsg
                }
            }
        }
    })
}