package lavoro.teamup.data.room.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lavoro.teamup.data.model.entry.ProductEntry

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(roomTransaction: RoomTransaction): Long

    @Query("SELECT * FROM transaction_table")
    suspend fun getList(): List<RoomTransaction>

    @Query("SELECT * FROM transaction_table WHERE id = :itemId")
    suspend fun getItemById(itemId: String): RoomTransaction

    @Query("DELETE FROM transaction_table where id = :itemId")
    suspend fun deleteItem(itemId: String)

    @Query("SELECT * FROM transaction_table ORDER BY id DESC LIMIT 1")
    suspend fun getLatestItem(): RoomTransaction


    @Query("DELETE FROM transaction_table")
    suspend fun clearList()

}
