package lavoro.teamup.data.model.user

import lavoro.teamup.core.ROLE_TEAM

data class RemoteUser(
    val uid: String? = "",
    val name: String? = "",
    val email: String? = "",
    val phone: String? = "",
    val roleId: Int? = ROLE_TEAM,
    val activated: Boolean? = false
)
