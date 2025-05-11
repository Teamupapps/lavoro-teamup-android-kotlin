package lavoro.teamup.companyprofile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import lavoro.teamup.R
import lavoro.teamup.core.view.updateColor
import lavoro.teamup.core.view.updateDrawable
import lavoro.teamup.core.view.visible
import lavoro.teamup.data.model.menu.CompanyMenu
import lavoro.teamup.databinding.ItemHeaderBinding
import lavoro.teamup.companyprofile.CompanyProfileViewEvent

class CompanyMenuAdapter(
    val event: MutableLiveData<CompanyProfileViewEvent> = MutableLiveData()
) : ListAdapter<CompanyMenu, CompanyMenuAdapter.MenuViewHolder>(CompanyMenuDiffUtilCallback()) {

    inner class MenuViewHolder(var binding: ItemHeaderBinding) : ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder =
        MenuViewHolder(
            ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = getItem(position)
        val context = holder.itemView.context

        with(holder.binding) {
            imgAction.updateDrawable(R.drawable.ic_arrow_nav, context)

            txtTitle.text = menu.title.asString(context)
            txtDesc.text = menu.desc.asString(context)

            imgAction.visible(isVisible = menu.isNav)
            txtDesc.visible(isVisible = menu.isSingleTitle == false)

            if (menu.isColored) txtTitle.updateColor(R.color.violet, context)

            holder.itemView.setOnClickListener {
                event.value = CompanyProfileViewEvent.OnMenuItemClick(menu.id)
            }
        }
    }
}

