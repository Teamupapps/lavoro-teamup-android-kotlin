package lavoro.teamup.teamlist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lavoro.teamup.data.repository.TeamRepository
import lavoro.teamup.teamlist.TeamViewModel

class TeamViewModelFactory(
    private val teamRepository: TeamRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(TeamViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            TeamViewModel(teamRepository) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
