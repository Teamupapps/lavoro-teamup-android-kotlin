package lavoro.teamup.notedetails.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import lavoro.teamup.data.repository.NoteRepository
import lavoro.teamup.notedetails.NoteDetailsViewModel

class NoteViewModelFactory(
    private val noteRepository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        if (modelClass.isAssignableFrom(NoteDetailsViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            NoteDetailsViewModel(noteRepository, extras.createSavedStateHandle()) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
