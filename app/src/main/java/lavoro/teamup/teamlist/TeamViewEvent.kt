package lavoro.teamup.teamlist


sealed class TeamViewEvent {
    data object GetTeam : TeamViewEvent()
    data class OnItemListSwitchCheck(val pos: Int, val active: Boolean) : TeamViewEvent()
}
