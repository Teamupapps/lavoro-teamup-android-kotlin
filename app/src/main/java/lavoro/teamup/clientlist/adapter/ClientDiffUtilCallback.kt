package lavoro.teamup.clientlist.adapter

import androidx.recyclerview.widget.DiffUtil
import lavoro.teamup.data.model.client.Client

class ClientDiffUtilCallback : DiffUtil.ItemCallback<Client>() {
    override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
        return oldItem.id == newItem.id
    }
}
