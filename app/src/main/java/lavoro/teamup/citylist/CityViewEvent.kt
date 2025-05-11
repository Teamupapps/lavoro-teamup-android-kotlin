package lavoro.teamup.citylist

sealed class CityViewEvent {
    data object OnStartGetCity : CityViewEvent()
    data object HideBottomSheet : CityViewEvent()
    data object OnMenuRefreshClick : CityViewEvent()
    data class OnMenuDeleteClick(val pos: Int) : CityViewEvent()
    data class OnListItemClick(val pos: Int) : CityViewEvent()
    data class OnUpdateTxtClick(val name: String) : CityViewEvent()
}
