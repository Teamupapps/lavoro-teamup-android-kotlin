package lavoro.teamup.data.model.client

import lavoro.teamup.data.model.entry.CityEntry

data class RemoteClient(
    val id: String? = "",
    val name: String? = "",
    val phone: String? = "",
    val cityEntry: CityEntry? = CityEntry("", "")
)
