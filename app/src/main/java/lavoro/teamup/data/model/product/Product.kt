package lavoro.teamup.data.model.product

import lavoro.teamup.data.model.entry.BrandEntry

data class Product(
    var id: String,
    val name: String,
    var brandEntry: BrandEntry
)
