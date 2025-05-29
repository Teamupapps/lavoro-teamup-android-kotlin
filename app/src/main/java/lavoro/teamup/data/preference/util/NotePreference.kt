package lavoro.teamup.data.preference.util

interface NotePreference {
    fun isNoteUsed(): Boolean
    fun displayTodayNotesOnly(): Boolean
}
