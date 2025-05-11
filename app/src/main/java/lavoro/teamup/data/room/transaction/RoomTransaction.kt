package lavoro.teamup.data.room.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import lavoro.teamup.data.model.entry.AssetEntry
import lavoro.teamup.data.model.entry.ClientEntry
import lavoro.teamup.data.model.entry.HistoryEntry
import lavoro.teamup.data.model.entry.ProductEntry

@Entity(tableName = "transaction_table", indices = [Index("id")])
data class RoomTransaction(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "asset_entry")
    val assetEntry: AssetEntry,

    @ColumnInfo(name = "pre_asset_entry")
    val preAssetEntry: AssetEntry,

    @ColumnInfo(name = "client_entry")
    val clientEntry: ClientEntry,

    @ColumnInfo(name = "product_entry")
    val productEntry: ProductEntry,

    @ColumnInfo(name = "history_entry")
    val historyEntry: HistoryEntry,

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "temp_item")
    val temp: Boolean,
)
