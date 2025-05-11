package lavoro.teamup.data.model.transaction

import lavoro.teamup.data.model.entry.AssetEntry
import lavoro.teamup.data.model.entry.ClientEntry
import lavoro.teamup.data.model.entry.HistoryEntry
import lavoro.teamup.data.model.entry.ProductEntry

data class RemoteTransaction(
    val id: String? = "",
    var assetEntry: AssetEntry? = AssetEntry(),
    var preAssetEntry: AssetEntry? = AssetEntry(),
    var clientEntry: ClientEntry? = ClientEntry(),
    var productEntry: ProductEntry? = ProductEntry(),
    var historyEntry: HistoryEntry? = HistoryEntry(),
    var note: String? = "",
    var temp: Boolean? = false
)
