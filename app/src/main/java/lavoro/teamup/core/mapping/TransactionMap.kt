package lavoro.teamup.core.mapping

import lavoro.teamup.data.model.entry.AssetEntry
import lavoro.teamup.data.model.entry.ClientEntry
import lavoro.teamup.data.model.entry.HistoryEntry
import lavoro.teamup.data.model.entry.ProductEntry
import lavoro.teamup.data.model.transaction.RemoteTransaction
import lavoro.teamup.data.model.transaction.Transaction
import lavoro.teamup.data.room.transaction.RoomTransaction

internal val RoomTransaction.toTransaction: Transaction
    get() = Transaction(
        id = this.id,
        assetEntry = this.assetEntry,
        preAssetEntry = this.preAssetEntry,
        clientEntry = this.clientEntry,
        productEntry = this.productEntry,
        historyEntry = this.historyEntry,
        note = this.note,
        temp = this.temp
    )
internal val RemoteTransaction.toTransaction: Transaction
    get() = Transaction(
        id = this.id ?: "",
        assetEntry = this.assetEntry ?: AssetEntry(),
        preAssetEntry = this.preAssetEntry ?: AssetEntry(),
        clientEntry = this.clientEntry ?: ClientEntry(),
        productEntry = this.productEntry ?: ProductEntry(),
        historyEntry = this.historyEntry ?: HistoryEntry(),
        note = this.note ?: "",
        temp = this.temp ?: false,
    )
internal val Transaction.toRemoteTransaction: RemoteTransaction
    get() = RemoteTransaction(
        id = this.id,
        assetEntry = this.assetEntry,
        preAssetEntry = this.preAssetEntry,
        clientEntry = this.clientEntry,
        productEntry = this.productEntry,
        historyEntry = this.historyEntry,
        note = this.note,
        temp = this.temp,
    )
internal val Transaction.toRoomTransaction: RoomTransaction
    get() = RoomTransaction(
        id = this.id,
        assetEntry = this.assetEntry,
        preAssetEntry = this.preAssetEntry,
        clientEntry = this.clientEntry,
        productEntry = this.productEntry,
        historyEntry = this.historyEntry,
        note = this.note,
        temp = this.temp,
    )

internal fun List<RoomTransaction>.toTransactionList(): List<Transaction> = this.flatMap {
    listOf(it.toTransaction)
}