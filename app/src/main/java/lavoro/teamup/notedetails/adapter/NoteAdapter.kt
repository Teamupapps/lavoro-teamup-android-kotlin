package lavoro.teamup.notedetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lavoro.teamup.R
import lavoro.teamup.core.bindDataWithHint
import lavoro.teamup.core.capitalized
import lavoro.teamup.core.view.displayDeleteAlertDialog
import lavoro.teamup.core.view.setMenuRedTitle
import lavoro.teamup.data.model.note.Note
import lavoro.teamup.databinding.ItemNoteBinding
import lavoro.teamup.transaction.itemlist.BaseTransactionListUtilViewEvent

class NoteAdapter(
    val event: MutableLiveData<BaseTransactionListUtilViewEvent> = MutableLiveData()
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffUtilCallback()) {

    inner class NoteViewHolder(var binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)

        with(holder.binding) {
            txtTitle.text = note.title

            txtDesc.text = bindDataWithHint(
                hint = holder.itemView.context.getString(R.string.date),
                data = note.historyEntry.creationDate +
                        ", ${holder.itemView.context.getString(R.string.by)} ${note.historyEntry.createdBy}"
            )

            root.setOnClickListener {
                it.inflateItemMenu(position)
            }
        }
    }

    private fun View.inflateItemMenu(position: Int) {
        PopupMenu(this@inflateItemMenu.context, this@inflateItemMenu).apply {

            inflate(R.menu.note_list_menu)

            menu.findItem(R.id.delete_menu).setMenuRedTitle()

            setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.update_item_menu -> event.value =
                        BaseTransactionListUtilViewEvent.OnNoteItemClick(pos = position)

                    R.id.refresh_menu -> event.value =
                        BaseTransactionListUtilViewEvent.OnMenuNoteListRefresh

                    R.id.delete_menu -> context.displayDeleteAlertDialog {
                        event.value = BaseTransactionListUtilViewEvent.OnMenuNoteListDelete(
                            pos = position
                        )
                    }
                }
                false
            }
            show()
        }
    }

}