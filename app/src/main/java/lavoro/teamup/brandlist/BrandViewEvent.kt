package lavoro.teamup.brandlist

sealed class BrandViewEvent {
    data object OnStartGetBrand : BrandViewEvent()
    data object HideBottomSheet : BrandViewEvent()
    data object OnMenuRefreshClick : BrandViewEvent()
    data class OnMenuDeleteClick(val pos: Int) : BrandViewEvent()
    data class OnListItemClick(val pos: Int) : BrandViewEvent()
    data class OnUpdateTxtClick(val brand: String) : BrandViewEvent()
}
