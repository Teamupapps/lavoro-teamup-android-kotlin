package lavoro.teamup.core.mapping

import lavoro.teamup.data.model.city.City
import lavoro.teamup.data.model.city.RemoteCity
import lavoro.teamup.data.room.city.RoomCity

internal val RoomCity.toCity: City
    get() = City(
        id = this.id,
        name = this.name
    )
internal val RemoteCity.toCity: City
    get() = City(
        id = this.id ?: "",
        name = this.name ?: ""
    )
internal val City.toRemoteCity: RemoteCity
    get() = RemoteCity(
        id = this.id,
        name = this.name,
    )
internal val City.toRoomCity: RoomCity
    get() = RoomCity(
        id = this.id,
        name = this.name
    )

internal fun List<RoomCity>.toCityList(): List<City> = this.flatMap {
    listOf(it.toCity)
}
