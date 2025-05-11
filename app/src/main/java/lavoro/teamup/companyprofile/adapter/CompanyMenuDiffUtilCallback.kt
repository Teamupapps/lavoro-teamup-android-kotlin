package lavoro.teamup.companyprofile.adapter

import androidx.recyclerview.widget.DiffUtil
import lavoro.teamup.data.model.menu.CompanyMenu

class CompanyMenuDiffUtilCallback : DiffUtil.ItemCallback<CompanyMenu>() {
    override fun areItemsTheSame(oldItem: CompanyMenu, newItem: CompanyMenu): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CompanyMenu, newItem: CompanyMenu): Boolean {
        return oldItem.id == newItem.id
    }
}
