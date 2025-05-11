package lavoro.teamup.core.mapping

import lavoro.teamup.data.model.entry.BrandEntry
import lavoro.teamup.data.model.product.Product
import lavoro.teamup.data.model.product.RemoteProduct
import lavoro.teamup.data.room.product.RoomProduct

internal val RoomProduct.toProduct: Product
    get() = Product(
        id = this.id,
        name = this.name,
        brandEntry = this.brandEntry
    )
internal val RemoteProduct.toProduct: Product
    get() = Product(
        id = this.id ?: "",
        name = this.name ?: "",
        brandEntry = this.brandEntry ?: BrandEntry()
    )
internal val Product.toRemoteProduct: RemoteProduct
    get() = RemoteProduct(
        id = this.id,
        name = this.name,
        brandEntry = this.brandEntry
    )
internal val Product.toRoomProduct: RoomProduct
    get() = RoomProduct(
        id = this.id,
        name = this.name,
        brandEntry = this.brandEntry
    )

internal fun List<RoomProduct>.toProductList(): List<Product> = this.flatMap {
    listOf(it.toProduct)
}
