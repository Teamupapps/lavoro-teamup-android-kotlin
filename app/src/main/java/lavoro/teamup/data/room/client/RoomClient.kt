package lavoro.teamup.data.room.client

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import lavoro.teamup.data.model.entry.CityEntry

@Entity(
    tableName = "client_table",
    indices = [Index("id")]
)
data class RoomClient(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "phone")
    var phone: String,

    @ColumnInfo(name = "city_entry")
    var cityEntry: CityEntry
)
