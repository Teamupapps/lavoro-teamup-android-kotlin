package lavoro.teamup.data.room.client

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateClient(client: RoomClient): Long

    @Query("SELECT * FROM client_table")
    suspend fun getClientList(): List<RoomClient>

    @Query("DELETE FROM client_table where id = :clientId")
    suspend fun deleteClient(clientId: String)

    @Query("DELETE FROM client_table")
    suspend fun clearList()

}
