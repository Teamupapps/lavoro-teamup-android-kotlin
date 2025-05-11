package lavoro.teamup.teamlist.adapter

import androidx.recyclerview.widget.DiffUtil
import lavoro.teamup.data.model.team.Team

class TeamDiffUtilCallback : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.uid == newItem.uid
    }
}
