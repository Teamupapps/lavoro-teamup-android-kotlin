package lavoro.teamup.data.room.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import lavoro.teamup.data.model.entry.HistoryEntry


@Entity(
    tableName = "notes",
    indices = [Index("id")]
)
data class RoomNote(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "history_entry")
    val historyEntry: HistoryEntry,

    @ColumnInfo(name = "only_admins")
    val onlyAdmins: Boolean
)
