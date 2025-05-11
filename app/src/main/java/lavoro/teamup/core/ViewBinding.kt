package lavoro.teamup.core

import android.content.Context
import lavoro.teamup.R
import java.util.Locale
import kotlin.math.roundToInt

internal fun String.plusQuan(context: Context?): String =
    this + " ${context?.getString(R.string.un)}"

internal fun String.plusCurrency(context: Context?): String =
    this + " ${context?.getString(R.string.le)}"

internal fun isDeviceLanguageArabic(): Boolean = Locale.getDefault().language.equals("ar")

internal fun Double.toFormatedString(signDisplay: Boolean = false): String = try {
    String.format(Locale.US, if (signDisplay) "%+,.2f" else "%,.2f", this@toFormatedString)
} catch (e: Exception) {
    Double.toString()
}

internal fun String.capitalized(): String = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault())
    else it.toString()
}

internal fun bindDataWithHint(
    hint: String,
    data: String
): String =
    if (data.isNotBlank()) "$hint: $data"
    else ""

internal fun Double.limitDouble(): Double = try {
    (this * 100.0).roundToInt() / 100.0
} catch (e: Exception) {
    this
}

internal fun getTotal(assetPrice: Double?, quantity: Double?): Double = try {
    ((assetPrice ?: 0.0) * (quantity ?: 0.0)).limitDouble()
} catch (ex: Exception) {
    0.0
}

internal fun getQuantity(assetPrice: Double?, total: Double?): Double = try {
    ((total ?: 0.0) / (assetPrice ?: 0.0)).limitDouble()
} catch (e: Exception) {
    0.0
}
internal fun getTotalEarning(buy: Double?, sell: Double?): Double = try {
    ((sell ?: 0.0) - (buy ?: 0.0)).limitDouble()
} catch (ex: Exception) {
    0.0
}