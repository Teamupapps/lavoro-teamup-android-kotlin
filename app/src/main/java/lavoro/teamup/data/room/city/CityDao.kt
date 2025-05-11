package lavoro.teamup.data.room.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(city: RoomCity): Long

    @Query("SELECT * FROM city_table")
    suspend fun getList(): List<RoomCity>

    @Query("DELETE FROM city_table where id = :cityId")
    suspend fun deleteCity(cityId: String)

    @Query("DELETE FROM city_table")
    suspend fun clearData()
}
