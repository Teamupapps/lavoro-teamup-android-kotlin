package lavoro.teamup.data.repository

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.team.Team

interface TeamRepository {
    suspend fun getTeamList(): Result<Exception, List<Team>>
    suspend fun updateTeamActiveStatus(uid: String, isActive: Boolean): Result<Exception, Unit>
}
