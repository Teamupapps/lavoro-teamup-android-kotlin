package lavoro.teamup.preferencesettings.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lavoro.teamup.data.reminder.ReminderAPI
import lavoro.teamup.data.repository.preferences.PreferenceRepository

class PreferenceViewModelFactory(
    private val preferenceRepository: PreferenceRepository,
    private val reminderAPI: ReminderAPI
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(PreferenceViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            PreferenceViewModel(preferenceRepository, reminderAPI) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
