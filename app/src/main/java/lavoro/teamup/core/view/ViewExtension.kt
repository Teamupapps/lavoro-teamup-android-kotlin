package lavoro.teamup.core.view

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import lavoro.teamup.R

internal fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

internal fun Activity.makeToast(msg: String) = Toast.makeText(
    this@makeToast, msg, Toast.LENGTH_SHORT
).show()

internal fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

internal fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

internal fun RecyclerView.setupListItemDecoration(context: Context) {
    addItemDecoration(
        DividerItemDecoration(
            context, DividerItemDecoration.VERTICAL
        )
    )
}

internal fun RecyclerView.setupListItemStaggeredView() {
    val sGridLayoutManager = StaggeredGridLayoutManager(
        2, StaggeredGridLayoutManager.VERTICAL
    )
    layoutManager = sGridLayoutManager
}

internal fun Fragment.relaunchCurrentView() {
    view?.post {
        findNavController().apply {
            val id = currentDestination?.id
            id?.let {
                popBackStack(it, true)
                navigate(it)
            }
        }
    }
}

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

internal fun displayDatePicker(
    fragmentManager: FragmentManager?,
    title: String,
    tag: String,
    action: ((Long) -> Unit)
) {
    val datePicker =
        MaterialDatePicker.Builder.datePicker().setTitleText(title)
            .build()
    fragmentManager?.let { manager ->
        datePicker.show(manager, tag)
        datePicker.addOnPositiveButtonClickListener {
            action.invoke(it)
        }
    }
}
