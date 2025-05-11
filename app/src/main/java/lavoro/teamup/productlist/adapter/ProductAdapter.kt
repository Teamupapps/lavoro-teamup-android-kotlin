package lavoro.teamup.productlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lavoro.teamup.R
import lavoro.teamup.clientlist.ClientViewEvent
import lavoro.teamup.core.view.displayDeleteAlertDialog
import lavoro.teamup.core.view.setMenuRedTitle
import lavoro.teamup.core.view.updateDrawable
import lavoro.teamup.data.model.product.Product
import lavoro.teamup.databinding.ItemHeaderBinding
import lavoro.teamup.productlist.ProductViewEvent

class ProductAdapter(
    val event: MutableLiveData<ProductViewEvent> = MutableLiveData()
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffUtilCallback()) {

    inner class ProductViewHolder(var binding: ItemHeaderBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        val context = holder.itemView.context

        with(holder.binding) {
            txtTitle.text = product.name

            txtDesc.text =
                product.brandEntry.name?.ifBlank { context.getString(R.string.product) }

            imgAction.updateDrawable(R.drawable.ic_menu, context)

            imgAction.setOnClickListener {
                it.inflateMenuList(position)
            }

            root.setOnClickListener {
                event.value = ProductViewEvent.OnListItemClick(position)
            }
        }
    }

    private fun View.inflateMenuList(position: Int) {
        PopupMenu(this@inflateMenuList.context, this).apply {
            inflate(R.menu.product_list_menu)

            menu.findItem(R.id.delete_menu).setMenuRedTitle()

            setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.expand_menu -> event.value = ProductViewEvent.OnStartGetProduct

                    R.id.refresh_menu -> event.value = ProductViewEvent.OnMenuRefreshClick

                    R.id.delete_menu -> context.displayDeleteAlertDialog {
                        event.value = ProductViewEvent.OnMenuDeleteClick(position)
                    }
                }
                false
            }
            show()
        }
    }

}
