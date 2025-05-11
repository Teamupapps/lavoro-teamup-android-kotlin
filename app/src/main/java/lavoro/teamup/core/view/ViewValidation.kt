package lavoro.teamup.core.view

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.preference.EditTextPreference
import lavoro.teamup.R
import java.util.Locale

internal const val MIN_PASS_DIG = 8
internal const val MAX_PASS_DIG = 16

internal const val MAX_ASSET_UNIT_PRICE_DIG = 8
internal const val MAX_ASSET_UNIT_NO_PRICE_DIG = 8
internal const val MAX_ASSET_TOTAL_PRICE_DIG = 16

internal const val MIN_TITLE_DIG = 2
internal const val MAX_TITLE_DIG = 16
internal const val MAX_NOTE_DIG = 80


internal fun String.isEmailAddress(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

internal fun String.isPhoneNumberValid(): Boolean = Patterns.PHONE.matcher(this).matches()

internal fun String.isQueryMatch(query: String?): Boolean =
    query?.lowercase(Locale.ROOT)?.let { this.lowercase(Locale.ROOT).contains(it) } == true

internal fun String.isValidLength(minDig: Int, maxDig: Int): Boolean =
    length in minDig..maxDig

internal fun String.isValidFormattedNumber(maxDig: Int): Boolean = try {
    if (
        this == "0" || this == "0.0" || this == "0.00" ||
        isBlank() ||
        startsWith("00") ||
        startsWith(".") ||
        endsWith(".") ||
        contains("E") ||
        isValidLength(minDig = 1, maxDig = maxDig) == false
    ) false
    else true
} catch (ex: Exception) {
    false
}


internal fun EditText.setEmailInput() {
    this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
}

internal fun EditText.setPasswordInput() {
    this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
}

internal fun EditText.setInputMaxLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}

internal fun EditText.setNumberDecimalInput() {
    this.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

}

internal fun EditText.setPhoneNumInput() {
    this.inputType = InputType.TYPE_CLASS_PHONE
}

internal fun EditTextPreference.setNumberDecimalInput() {
    setOnBindEditTextListener { editText ->
        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

    }
}

internal fun Context.displayDeleteAlertDialog(
    action: (() -> Unit)? = null
) {
    displayAlertDialog(
        title = getString(R.string.delete_permanently),
        pTitle = getString(R.string.confirm),
        pAction = action
    )
}

internal fun Context.displayAlertDialog(
    title: String,
    message: String? = null,
    pTitle: String? = null,
    pAction: (() -> Unit)? = null
) {
    AlertDialog.Builder(this@displayAlertDialog)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(pTitle) { _, _ ->
            pAction?.invoke()
        }.show()
}