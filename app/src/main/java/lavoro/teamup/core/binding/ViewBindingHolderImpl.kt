package lavoro.teamup.core.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.viewbinding.ViewBinding

open class ViewBindingHolderImpl<T : ViewBinding> : ViewBindingHolder<T>, LifecycleObserver {

    override var binding: T? = null

    private var lifecycle: Lifecycle? = null

    override fun initBinding(binding: T, fragment: Fragment, onBound: (T.() -> Unit)?): View {
        this.binding = binding

        lifecycle = fragment.viewLifecycleOwner.lifecycle

        lifecycle?.addObserver(this)

        onBound?.invoke(binding)

        return binding.root
    }

    override fun destroyBinding() {
        lifecycle?.removeObserver(this)

        lifecycle = null
        binding = null
    }
}
