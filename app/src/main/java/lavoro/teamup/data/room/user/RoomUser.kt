package lavoro.teamup.data.room.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import lavoro.teamup.core.CURRENT_USER_ID

@Entity(tableName = "user_table")
data class RoomUser(
    @ColumnInfo(name = "uid")
    val uid: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "role_id")
    val roleId: Int,

    @ColumnInfo(name = "activated")
    val activated: Boolean
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_USER_ID
}
