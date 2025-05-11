package lavoro.teamup.core.mapping

import lavoro.teamup.data.model.brand.Brand
import lavoro.teamup.data.model.brand.RemoteBrand
import lavoro.teamup.data.room.brand.RoomBrand

internal val RoomBrand.toBrand: Brand
    get() = Brand(
        id = this.id,
        name = this.name
    )
internal val RemoteBrand.toBrand: Brand
    get() = Brand(
        id = this.id ?: "",
        name = this.name ?: ""
    )
internal val Brand.toRemoteBrand: RemoteBrand
    get() = RemoteBrand(
        id = this.id,
        name = this.name
    )
internal val Brand.toRoomBrand: RoomBrand
    get() = RoomBrand(
        id = this.id,
        name = this.name
    )

internal fun List<RoomBrand>.toBrandList(): List<Brand> = this.flatMap {
    listOf(it.toBrand)
}
