package lavoro.teamup.home

sealed class HomeActivityEvent {
    data object OnStartGetUser : HomeActivityEvent()
}