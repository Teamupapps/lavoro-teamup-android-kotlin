package lavoro.teamup.data.room.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: RoomProduct): Long

    @Query("SELECT * FROM product_table")
    suspend fun getList(): List<RoomProduct>

    @Query("DELETE FROM product_table where id = :productId")
    suspend fun deleteProduct(productId: String)

    @Query("DELETE FROM product_table")
    suspend fun clearList()

}
