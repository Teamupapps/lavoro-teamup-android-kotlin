package lavoro.teamup.data.model.note

import lavoro.teamup.data.model.entry.HistoryEntry

data class RemoteNote(
    val id: String? = "",
    val title: String? = "",
    val historyEntry: HistoryEntry? = HistoryEntry("",""),
    var onlyAdmins: Boolean? = false
)