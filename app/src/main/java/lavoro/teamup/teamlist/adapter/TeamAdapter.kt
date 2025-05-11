package lavoro.teamup.teamlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lavoro.teamup.R
import lavoro.teamup.core.ROLE_ADMIN
import lavoro.teamup.core.ROLE_OWNER
import lavoro.teamup.core.view.enable
import lavoro.teamup.data.model.team.Team
import lavoro.teamup.databinding.ItemTeamBinding
import lavoro.teamup.teamlist.TeamViewEvent

class TeamAdapter(
    val event: MutableLiveData<TeamViewEvent> = MutableLiveData()
) : ListAdapter<Team, TeamAdapter.TeamViewHolder>(TeamDiffUtilCallback()) {

    inner class TeamViewHolder(var binding: ItemTeamBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(
            ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = getItem(position)
        with(holder.binding) {

            txtTitle.text = team.name

            txtDes.text = team.email

            team.activated.let {
                switchAction.isChecked = it
                txtTitle.enable(it)
                txtDes.enable(it)
            }

            switchAction.setOnCheckedChangeListener { buttonView, isChecked ->
                if (buttonView?.isPressed == true) event.value =
                    TeamViewEvent.OnItemListSwitchCheck(
                        active = isChecked, pos = position
                    )
            }
        }
    }
}