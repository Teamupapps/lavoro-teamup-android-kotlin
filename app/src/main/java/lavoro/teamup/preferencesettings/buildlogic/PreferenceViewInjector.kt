package lavoro.teamup.preferencesettings.buildlogic

import android.app.Application
import lavoro.teamup.core.base.BaseViewInjector
import lavoro.teamup.data.reminder.ReminderAPIImpl

class PreferenceViewInjector(
    app: Application
) : BaseViewInjector(app) {
    fun provideViewModelFactory() = PreferenceViewModelFactory(
        preferenceRepository = getPreferenceRepository(),
        reminderAPI = ReminderAPIImpl(getApplication())
    )
}
