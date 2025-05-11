package lavoro.teamup.data.room.brand

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface BrandDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(brand: RoomBrand): Long

    @Query("SELECT * FROM brand_table")
    suspend fun getList(): List<RoomBrand>

    @Query("DELETE FROM brand_table where id = :brandId")
    suspend fun deleteBrand(brandId: String)

    @Query("DELETE FROM brand_table")
    suspend fun clearList()
}
