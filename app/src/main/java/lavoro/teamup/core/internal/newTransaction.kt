package lavoro.teamup.core.internal

import android.content.Context
import lavoro.teamup.R
import lavoro.teamup.core.getTotal
import lavoro.teamup.core.toFormatedString
import lavoro.teamup.data.model.entry.AssetEntry
import lavoro.teamup.data.model.entry.ClientEntry
import lavoro.teamup.data.model.entry.HistoryEntry
import lavoro.teamup.data.model.entry.ProductEntry
import lavoro.teamup.data.model.transaction.Transaction

internal fun newTransaction() = Transaction(
    id = "",
    assetEntry = AssetEntry(),
    preAssetEntry = AssetEntry(),
    clientEntry = ClientEntry(),
    productEntry = ProductEntry(),
    historyEntry = HistoryEntry(),
    note = "",
    temp = false
)

internal fun HistoryEntry.toHistoryText(context: Context): String =
    "${context.getString(R.string.by)}: ${createdBy}\n" +
            "${context.getString(R.string.date)}: ${creationDate}."

internal fun Transaction.toShareText(pricePadding: Boolean = true): String {
    val pLines = if (pricePadding) "\n\n" else "\n"
    return "Type: ${if (assetEntry.sell == true) "Sell" else "Buy"}\n" +
            "Create By: ${historyEntry.createdBy}\n" +
            "Create Date: ${historyEntry.creationDate}\n" +
            "Price: ${assetEntry.unitPrice}\n" +
            "Quantity: ${assetEntry.quantity}\n" +
            "Total: ${
                getTotal(
                    assetEntry.unitPrice,
                    assetEntry.quantity
                ).toFormatedString()
            }$pLines" +
            "Client: ${clientEntry.name}-${clientEntry.city}\n" +
            "Note: ${note}."
}
