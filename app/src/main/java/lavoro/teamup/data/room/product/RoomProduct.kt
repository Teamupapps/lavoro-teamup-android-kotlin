package lavoro.teamup.data.room.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import lavoro.teamup.data.model.entry.BrandEntry


@Entity(
    tableName = "product_table",
    indices = [Index("id")]
)
data class RoomProduct(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "brand_entry")
    var brandEntry: BrandEntry
)
