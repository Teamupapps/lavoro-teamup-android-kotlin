package lavoro.teamup.core.wrapper

import android.content.Context
import androidx.annotation.StringRes

sealed class UIResource {

    class StringResource(
        @StringRes vararg val resId: Int
    ) : UIResource()

    fun asString(context: Context?): String? = when (this) {
        is StringResource -> context?.getString(resId[0])
    }

    fun asStringList(context: Context?): String = when (this) {
        is StringResource -> {
            var title = ""
            for (element in resId) title += context?.getString(element) + "\\"
            title
        }
    }
}
