package lavoro.teamup.data.model.note

import lavoro.teamup.data.model.entry.HistoryEntry

data class Note(
    var id: String,
    val title: String,
    val historyEntry: HistoryEntry,
    var onlyAdmins: Boolean
)

