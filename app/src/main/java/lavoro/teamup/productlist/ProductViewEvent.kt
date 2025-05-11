package lavoro.teamup.productlist

sealed class ProductViewEvent {
    data object OnStartGetProduct : ProductViewEvent()
    data object GetBrandList : ProductViewEvent()
    data object HideBottomSheet : ProductViewEvent()
    data class OnSpinnerBrandSelect(val pos: Int) : ProductViewEvent()
    data class OnUpdateTxtClick(val product: String) : ProductViewEvent()
    data class OnListItemClick(val pos: Int) : ProductViewEvent()
    data object OnMenuRefreshClick : ProductViewEvent()
    data class OnMenuDeleteClick(val pos: Int) : ProductViewEvent()
}
