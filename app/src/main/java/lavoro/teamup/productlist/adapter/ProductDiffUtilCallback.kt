package lavoro.teamup.productlist.adapter

import androidx.recyclerview.widget.DiffUtil
import lavoro.teamup.data.model.product.Product

class ProductDiffUtilCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }
}
