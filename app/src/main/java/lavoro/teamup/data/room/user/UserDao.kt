package lavoro.teamup.data.room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lavoro.teamup.core.CURRENT_USER_ID

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: RoomUser): Long

    @Query("SELECT EXISTS(SELECT * FROM user_table)")
    suspend fun isExist(): Boolean

    @Query("select activated from user_table where id = $CURRENT_USER_ID")
    suspend fun isActive(): Boolean

    @Query("select * from user_table where id = $CURRENT_USER_ID")
    suspend fun getUser(): RoomUser?

    @Query("select role_id from user_table where id = $CURRENT_USER_ID")
    suspend fun getRoleId(): Int?

    @Query("DELETE FROM user_table")
    suspend fun deleteUser()
}
