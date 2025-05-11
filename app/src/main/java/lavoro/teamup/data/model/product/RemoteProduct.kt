package lavoro.teamup.data.model.product

import lavoro.teamup.data.model.entry.BrandEntry

data class RemoteProduct(
    val id: String? = "",
    var name: String? = "",
    var brandEntry: BrandEntry? = BrandEntry("", "")
)
