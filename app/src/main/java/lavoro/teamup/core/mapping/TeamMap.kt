package lavoro.teamup.core.mapping

import lavoro.teamup.core.ROLE_TEAM
import lavoro.teamup.data.model.team.RemoteTeam
import lavoro.teamup.data.model.team.Team

internal val RemoteTeam.toTeam: Team
    get() = Team(
        uid = this.uid ?: "",
        name = this.name ?: "",
        email = this.email ?: "",
        roleId = this.roleId ?: ROLE_TEAM,
        activated = this.activated ?: false
    )

