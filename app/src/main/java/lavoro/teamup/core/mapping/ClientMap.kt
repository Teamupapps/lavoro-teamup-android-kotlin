package lavoro.teamup.core.mapping

import lavoro.teamup.data.model.entry.CityEntry
import lavoro.teamup.data.model.client.Client
import lavoro.teamup.data.model.client.RemoteClient
import lavoro.teamup.data.room.client.RoomClient

internal val RoomClient.toClient: Client
    get() = Client(
        id = this.id,
        name = this.name,
        phone = this.phone,
        cityEntry = this.cityEntry
    )
internal val RemoteClient.toClient: Client
    get() = Client(
        id = this.id ?: "",
        name = this.name ?: "",
        phone = this.phone ?: "",
        cityEntry = this.cityEntry ?: CityEntry()
    )
internal val Client.toRemoteClient: RemoteClient
    get() = RemoteClient(
        id = this.id,
        name = this.name,
        phone = this.phone,
        cityEntry = this.cityEntry
    )
internal val Client.toRoomClient: RoomClient
    get() = RoomClient(
        id = this.id,
        name = this.name,
        phone = this.phone,
        cityEntry = this.cityEntry
    )

internal fun List<RoomClient>.toClientList(): List<Client> = this.flatMap {
    listOf(it.toClient)
}
