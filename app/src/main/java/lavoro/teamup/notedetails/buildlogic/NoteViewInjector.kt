package lavoro.teamup.notedetails.buildlogic

import android.app.Application
import lavoro.teamup.core.base.BaseViewInjector

class NoteViewInjector(
    app: Application
) : BaseViewInjector(app) {
    fun provideViewModelFactory() = NoteViewModelFactory(getNoteRepository())
}
