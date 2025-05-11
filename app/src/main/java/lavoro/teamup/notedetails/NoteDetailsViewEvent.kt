package lavoro.teamup.notedetails

sealed class NoteDetailsViewEvent {
    data object OnStartGetNote : NoteDetailsViewEvent()
    data class OnUpdateTxtClick(val title: String) : NoteDetailsViewEvent()
    data class OnAdminSwitchCheck(var admin: Boolean) : NoteDetailsViewEvent()

}
