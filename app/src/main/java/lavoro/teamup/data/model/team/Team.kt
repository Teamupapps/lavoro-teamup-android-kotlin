package lavoro.teamup.data.model.team

data class Team(
    val uid: String,
    val name: String,
    val email: String,
    val roleId: Int,
    val activated: Boolean
)
