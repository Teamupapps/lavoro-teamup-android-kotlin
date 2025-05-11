package lavoro.teamup.data.model.stock

import lavoro.teamup.data.model.entry.ProductEntry

data class TransactionStock(
    var id: String,
    var productEntry: ProductEntry,
    var quantity: Double
)
