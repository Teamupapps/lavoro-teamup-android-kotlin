package lavoro.teamup.brandlist.adapter

import androidx.recyclerview.widget.DiffUtil
import lavoro.teamup.data.model.brand.Brand

class BrandDiffUtilCallback : DiffUtil.ItemCallback<Brand>() {
    override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
        return oldItem.id == newItem.id
    }
}
