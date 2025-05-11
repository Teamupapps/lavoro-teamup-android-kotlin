package lavoro.teamup.citylist.adapter

import androidx.recyclerview.widget.DiffUtil
import lavoro.teamup.data.model.city.City

class CityDiffUtilCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.id == newItem.id
    }
}
