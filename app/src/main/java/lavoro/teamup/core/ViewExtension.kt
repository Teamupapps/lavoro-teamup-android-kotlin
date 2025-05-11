package lavoro.teamup.core

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import lavoro.teamup.R


internal fun setActionBarFontTitle(toolbar: androidx.appcompat.widget.Toolbar) = try {
    for (i in 0 until toolbar.childCount) {
        val view = toolbar.getChildAt(i)
        if (view is TextView && view.text == toolbar.title) {
            view.typeface = ResourcesCompat.getFont(toolbar.context, R.font.tango_bold)
            break
        }
    }
} catch (ex: Exception) {
    ex.printStackTrace()
}