package lavoro.teamup.teamlist.buildlogic

import android.app.Application
import lavoro.teamup.core.base.BaseViewInjector

open class TeamViewInjector(
    app: Application
) : BaseViewInjector(app) {
    fun provideTeamListViewModelFactory() = TeamViewModelFactory(getTeamRepository())
}
