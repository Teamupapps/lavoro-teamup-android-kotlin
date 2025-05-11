package lavoro.teamup.clientlist


sealed class ClientViewEvent {
    data object OnStartGetClient : ClientViewEvent()
    data object GetCityList : ClientViewEvent()
    data object HideBottomSheet : ClientViewEvent()
    data class OnSpinnerCitySelect(val pos: Int) : ClientViewEvent()
    data class OnListItemClick(val pos: Int) : ClientViewEvent()
    data class OnUpdateTxtClick(val name: String, val phone: String) : ClientViewEvent()
    data object OnMenuRefreshClick : ClientViewEvent()
    data class OnMenuDeleteClick(val pos: Int) : ClientViewEvent()
    data class OnMenuDialClick(val pos: Int) : ClientViewEvent()
}
