package lavoro.teamup.data.room.brand

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "brand_table",
    indices = [Index("id")]
)
data class RoomBrand(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    var name: String,
)
