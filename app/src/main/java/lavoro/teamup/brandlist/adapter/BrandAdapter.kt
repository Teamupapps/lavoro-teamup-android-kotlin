package lavoro.teamup.brandlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lavoro.teamup.R
import lavoro.teamup.brandlist.BrandViewEvent
import lavoro.teamup.core.capitalized
import lavoro.teamup.core.view.displayDeleteAlertDialog
import lavoro.teamup.core.view.setMenuRedTitle
import lavoro.teamup.core.view.updateDrawable
import lavoro.teamup.data.model.brand.Brand
import lavoro.teamup.databinding.ItemHeaderBinding

class BrandAdapter(
    val event: MutableLiveData<BrandViewEvent> = MutableLiveData()
) : ListAdapter<Brand, BrandAdapter.BrandViewHolder>(BrandDiffUtilCallback()) {

    inner class BrandViewHolder(var binding: ItemHeaderBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder =
        BrandViewHolder(
            ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = getItem(position)
        val context = holder.itemView.context

        with(holder.binding) {
            txtTitle.text = brand.name

            txtDesc.text = context.getString(R.string.brand)

            imgAction.updateDrawable(R.drawable.ic_menu, context)

            imgAction.setOnClickListener {
                it.inflateMenuList(position)
            }

            root.setOnClickListener {
                event.value = BrandViewEvent.OnListItemClick(position)
            }
        }
    }

    private fun View.inflateMenuList(position: Int) {
        PopupMenu(this@inflateMenuList.context, this).apply {
            inflate(R.menu.brand_list_menu)

            menu.findItem(R.id.delete_menu).setMenuRedTitle()

            setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.expand_menu -> event.value = BrandViewEvent.OnStartGetBrand

                    R.id.refresh_menu -> event.value = BrandViewEvent.OnMenuRefreshClick

                    R.id.delete_menu -> context.displayDeleteAlertDialog {
                        event.value = BrandViewEvent.OnMenuDeleteClick(position)
                    }
                }
                false
            }
            show()
        }
    }

}
