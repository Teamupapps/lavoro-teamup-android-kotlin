package lavoro.teamup.data.model.team

import lavoro.teamup.core.ROLE_TEAM

data class RemoteTeam(
    val uid: String? = "",
    val name: String? = "",
    val email: String? = "",
    val roleId: Int? = ROLE_TEAM,
    val activated: Boolean? = false
)
