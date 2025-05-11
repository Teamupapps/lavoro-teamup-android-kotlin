package lavoro.teamup.data.model.entry

data class AssetEntry(
    val unitPrice: Double? = 0.0,
    var quantity: Double? = 0.0,
    var sell: Boolean? = true,
)
