package lavoro.teamup.data.model.user

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val phone: String,
    val roleId: Int,
    val activated: Boolean
)