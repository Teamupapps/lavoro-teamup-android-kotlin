package lavoro.teamup.data.model.transaction

import lavoro.teamup.data.model.entry.AssetEntry
import lavoro.teamup.data.model.entry.ClientEntry
import lavoro.teamup.data.model.entry.HistoryEntry
import lavoro.teamup.data.model.entry.ProductEntry

data class Transaction(
    var id: String,
    var assetEntry: AssetEntry,
    var preAssetEntry: AssetEntry,
    var clientEntry: ClientEntry,
    var productEntry: ProductEntry,
    val historyEntry: HistoryEntry,
    val note: String,
    var temp: Boolean,
)

