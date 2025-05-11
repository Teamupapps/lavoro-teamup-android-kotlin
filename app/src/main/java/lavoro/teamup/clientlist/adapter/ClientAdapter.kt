package lavoro.teamup.clientlist.adapter

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
import lavoro.teamup.data.model.client.Client
import lavoro.teamup.databinding.ItemHeaderBinding

class ClientAdapter(
    val event: MutableLiveData<ClientViewEvent> = MutableLiveData()
) : ListAdapter<Client, ClientAdapter.ClientViewHolder>(ClientDiffUtilCallback()) {

    inner class ClientViewHolder(var binding: ItemHeaderBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder =
        ClientViewHolder(
            ItemHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = getItem(position)
        val context = holder.itemView.context

        with(holder.binding) {
            txtTitle.text = client.name

            txtDesc.text = client.cityEntry.name?.ifBlank {
                client.phone.ifBlank { context.getString(R.string.client) }
            }

            imgAction.updateDrawable(R.drawable.ic_menu, context)

            imgAction.setOnClickListener {
                it.inflateMenuList(position)
            }

            root.setOnClickListener {
                event.value = ClientViewEvent.OnListItemClick(position)
            }

        }
    }

    private fun View.inflateMenuList(position: Int) {
        PopupMenu(this@inflateMenuList.context, this).apply {
            inflate(R.menu.client_list_menu)

            menu.findItem(R.id.delete_menu).setMenuRedTitle()

            setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.expand_menu -> event.value = ClientViewEvent.OnStartGetClient

                    R.id.refresh_menu -> event.value = ClientViewEvent.OnMenuRefreshClick

                    R.id.dial_menu -> event.value = ClientViewEvent.OnMenuDialClick(
                        pos = position
                    )

                    R.id.delete_menu -> context.displayDeleteAlertDialog {
                        event.value = ClientViewEvent.OnMenuDeleteClick(position)
                    }

                }
                false
            }
            show()
        }
    }

}
