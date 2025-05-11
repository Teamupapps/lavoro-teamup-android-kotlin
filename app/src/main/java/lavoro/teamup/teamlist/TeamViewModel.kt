package lavoro.teamup.teamlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lavoro.teamup.R
import lavoro.teamup.core.base.BaseViewModel
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.team.Team
import lavoro.teamup.data.repository.TeamRepository

class TeamViewModel(
    private val teamRepository: TeamRepository
) : BaseViewModel<TeamViewEvent>() {

    private val teamListState = MutableLiveData<List<Team>>()
    val teamList: LiveData<List<Team>> get() = teamListState

    private val updateState = MutableLiveData<Unit>()
    val updated: LiveData<Unit> get() = updateState

    override fun handleEvent(event: TeamViewEvent) {
        when (event) {
            is TeamViewEvent.GetTeam -> getTeamList()
            is TeamViewEvent.OnItemListSwitchCheck -> updateTeam(event.active, event.pos)
        }
    }

    private fun getTeamList() = viewModelScope.launch {
        showLoading()
        when (val result = teamRepository.getTeamList()) {
            is Result.Error -> result.error.message.actionExceptionMsg(error = {
                showError(R.string.cannot_read_remote_data)
            })

            is Result.Value -> teamListState.value = result.value.sortedByDescending {
                it.activated
            }
        }
        hideLoading()
    }

    private fun updateTeam(active: Boolean, pos: Int) = viewModelScope.launch {
        teamListState.value?.get(pos)?.uid?.let {
            showLoading()
            when (val result = teamRepository.updateTeamActiveStatus(
                uid = it, active
            )) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = {
                    showError(R.string.cannot_update_remote_data)
                })

                is Result.Value -> updateState.value = Unit
            }
            hideLoading()
        }

    }
}