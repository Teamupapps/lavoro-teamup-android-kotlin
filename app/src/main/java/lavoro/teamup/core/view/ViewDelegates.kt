package lavoro.teamup.core.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.text.parseAsHtml
import lavoro.teamup.R
import lavoro.teamup.core.isDeviceLanguageArabic
import java.util.Locale


internal fun ImageView.updateDrawable(@DrawableRes drawable: Int, context: Context?) =
    setImageDrawable(context?.let { ContextCompat.getDrawable(it, drawable) })

internal fun TextView.updateColor(@ColorRes intColor: Int, context: Context) =
    setTextColor(ContextCompat.getColor(context, intColor))

internal fun TextView.updatePaintFlags(labeled: Boolean) =
    if (labeled) paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else Unit

internal fun MenuItem.setMenuRedTitle() = try {
    val hexColor =
        Integer.toHexString(Color.parseColor("#c46e6e")).toUpperCase(Locale.ROOT).substring(2)
    val html = "<font color='#$hexColor'>$title</font>"
    this.title = html.parseAsHtml()
} catch (ex: Exception) {
    ex.printStackTrace()
}

internal fun TextView.setLocalizedHintDrawable(
    @DrawableRes drawable: Int,
    isMirrored: Boolean = false
) {
    var isAr = isDeviceLanguageArabic()

    if (isMirrored) isAr = !isAr

    if (isAr) this.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)
    else this.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
}

internal fun TextView.setHintDrawablePNType(
    income: Boolean, context: Context
) = try {
    fun setTintColor(
        @ColorRes color: Int, context: Context
    ) {
        if (isDeviceLanguageArabic()) this.compoundDrawables[2].setTint(
            ContextCompat.getColor(
                context,
                color
            )
        )
        else this.compoundDrawables[0].setTint(ContextCompat.getColor(context, color))
    }

    if (income) {
        setLocalizedHintDrawable(R.drawable.ic_arrow_up, isMirrored = true)
        setTintColor(R.color.green, context)
    } else {
        setLocalizedHintDrawable(R.drawable.ic_arrow_down, isMirrored = true)
        setTintColor(R.color.violet, context)
    }

} catch (ex: Exception) {
    ex.printStackTrace()
}

internal fun onViewClickUpdateExpanding(
    expandingGroup: ViewGroup,
    onInvisibleAction: (() -> Unit)? = null,
    onVisibleAction: (() -> Unit)? = null
) {
    if (expandingGroup.visibility == View.VISIBLE) onVisibleAction?.invoke()
    else onInvisibleAction?.invoke()
}