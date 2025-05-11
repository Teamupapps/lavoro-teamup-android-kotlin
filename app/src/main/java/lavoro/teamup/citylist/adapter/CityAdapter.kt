package lavoro.teamup.citylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lavoro.teamup.R
import lavoro.teamup.citylist.CityViewEvent
import lavoro.teamup.core.view.displayDeleteAlertDialog
import lavoro.teamup.core.view.setMenuRedTitle
import lavoro.teamup.core.view.updateDrawable
import lavoro.teamup.data.model.city.City
import lavoro.teamup.databinding.ItemHeaderBinding

class CityAdapter(
    val event: MutableLiveData<CityViewEvent> = MutableLiveData()
) : ListAdapter<City, CityAdapter.CityViewHolder>(CityDiffUtilCallback()) {

    inner class CityViewHolder(var binding: ItemHeaderBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder(
            ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)
        val context = holder.itemView.context

        with(holder.binding) {
            txtTitle.text = city.name

            txtDesc.text = context.getString(R.string.city)

            imgAction.updateDrawable(R.drawable.ic_menu, context)
            imgAction.setOnClickListener {
                it.inflateMenuList(position)
            }
            root.setOnClickListener {
                event.value = CityViewEvent.OnListItemClick(position)
            }
        }
    }

    private fun View.inflateMenuList(position: Int) {
        PopupMenu(this@inflateMenuList.context, this).apply {
            inflate(R.menu.city_list_menu)

            menu.findItem(R.id.delete_menu).setMenuRedTitle()

            setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.expand_menu -> event.value = CityViewEvent.OnStartGetCity

                    R.id.refresh_menu -> event.value = CityViewEvent.OnMenuRefreshClick

                    R.id.delete_menu -> context.displayDeleteAlertDialog {
                        event.value = CityViewEvent.OnMenuDeleteClick(position)
                    }
                }
                false
            }
            show()
        }
    }

}
