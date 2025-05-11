package lavoro.teamup.data.model.client

import lavoro.teamup.data.model.entry.CityEntry

data class Client(
    var id: String,
    var name: String,
    var phone: String,
    var cityEntry: CityEntry
)
