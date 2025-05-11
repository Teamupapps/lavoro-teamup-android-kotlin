package lavoro.teamup.core.mapping

import lavoro.teamup.data.model.entry.HistoryEntry
import lavoro.teamup.data.model.note.Note
import lavoro.teamup.data.model.note.RemoteNote
import lavoro.teamup.data.room.note.RoomNote

internal val Note.toRoomNote: RoomNote
    get() = RoomNote(
        id = this.id,
        title = this.title,
        historyEntry = this.historyEntry,
        onlyAdmins = this.onlyAdmins
    )
internal val RoomNote.toNote: Note
    get() = Note(
        id = this.id,
        title = this.title,
        historyEntry = this.historyEntry,
        onlyAdmins = this.onlyAdmins
    )
internal val RemoteNote.toNote: Note
    get() = Note(
        id = this.id ?: "",
        title = this.title ?: "",
        historyEntry = this.historyEntry ?: HistoryEntry(),
        onlyAdmins = this.onlyAdmins ?: false
    )
internal val Note.toRemoteNote: RemoteNote
    get() = RemoteNote(
        id = this.id,
        title = this.title,
        historyEntry = this.historyEntry,
        onlyAdmins = this.onlyAdmins
    )

internal fun List<RoomNote>.toNoteList(): List<Note> =
    this.flatMap {
        listOf(it.toNote)
    }