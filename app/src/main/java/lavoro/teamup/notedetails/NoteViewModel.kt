package lavoro.teamup.notedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lavoro.teamup.R
import lavoro.teamup.core.base.BaseViewModel
import lavoro.teamup.core.getCalendarDateTime
import lavoro.teamup.core.getSystemTimeMillis
import lavoro.teamup.core.view.MAX_NOTE_DIG
import lavoro.teamup.core.view.MIN_TITLE_DIG
import lavoro.teamup.core.view.isValidLength
import lavoro.teamup.core.wrapper.Event
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.entry.HistoryEntry
import lavoro.teamup.data.model.note.Note
import lavoro.teamup.data.repository.NoteRepository

class NoteViewModel(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteDetailsViewEvent>() {

    private val navNoteId: String? = savedStateHandle["noteId"]

    internal val adminSwitchState = MutableLiveData<Boolean>()

    internal val noteValidateState = MutableLiveData<Boolean>()

    private val noteState = MutableLiveData<Note>()
    val note: LiveData<Note> get() = noteState

    private val updateState = MutableLiveData<Event<Unit>>()
    val updated: LiveData<Event<Unit>> get() = updateState

    override fun handleEvent(event: NoteDetailsViewEvent) {
        when (event) {
            is NoteDetailsViewEvent.OnStartGetNote -> navNoteId?.let {

                updateAdministrationState(isAdmin = false)

                if (it.isBlank()) setupNewNote()
                else getNote(it)
            }

            is NoteDetailsViewEvent.OnUpdateTxtClick -> updateNote(event.title)

            is NoteDetailsViewEvent.OnAdminSwitchCheck -> updateAdministrationState(event.admin)
        }
    }

    private fun setupNewNote() {
        noteState.value = Note("", "", HistoryEntry("", ""), false)
    }

    private fun getNote(noteId: String) = viewModelScope.launch {
        showLoading()
        when (val result = noteRepository.getNoteById(noteId)) {
            is Result.Error -> result.error.message.actionExceptionMsg(error = {
                showError(R.string.note_list_error)
            })

            is Result.Value -> {
                noteState.value = result.value

                updateAdministrationState(isAdmin = result.value.onlyAdmins)

            }
        }
        hideLoading()
    }

    private fun updateNote(title: String) = viewModelScope.launch {
        noteState.value?.let {

            if (validNote(note = title) == false) return@launch

            showLoading()

            if (it.id.isBlank()) {
                it.id = getSystemTimeMillis()
                it.historyEntry.creationDate = getCalendarDateTime()
            }

            when (val result = noteRepository.updateNote(
                it.copy(
                    title = title,
                    onlyAdmins = adminSwitchState.value ?: false
                )
            )) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = {
                    showError(R.string.cannot_update_entries)
                })

                is Result.Value -> updateState.value = Event(Unit)
            }
        }
        hideLoading()
    }

    private fun updateAdministrationState(isAdmin: Boolean) {
        adminSwitchState.value = isAdmin
    }

    private fun validNote(note: String): Boolean =
        if (note.isValidLength(minDig = MIN_TITLE_DIG, maxDig = MAX_NOTE_DIG)) {
            noteValidateState.value = true
            true
        } else {
            noteValidateState.value = false
            false
        }
}