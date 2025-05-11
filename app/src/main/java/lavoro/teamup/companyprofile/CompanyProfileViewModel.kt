package lavoro.teamup.companyprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lavoro.teamup.R
import lavoro.teamup.core.MENU_BRAND
import lavoro.teamup.core.MENU_CITY
import lavoro.teamup.core.MENU_CLIENT
import lavoro.teamup.core.MENU_PRODUCT
import lavoro.teamup.core.base.BaseViewModel
import lavoro.teamup.core.wrapper.Event
import lavoro.teamup.core.wrapper.UIResource
import lavoro.teamup.data.model.menu.CompanyMenu

class CompanyProfileViewModel : BaseViewModel<CompanyProfileViewEvent>() {

    private val menuListState = MutableLiveData<List<CompanyMenu>>()
    val menuList: LiveData<List<CompanyMenu>> get() = menuListState

    private val editMenuState = MutableLiveData<Event<Int>>()
    val editMenu: LiveData<Event<Int>> get() = editMenuState

    override fun handleEvent(event: CompanyProfileViewEvent) {
        when (event) {
            is CompanyProfileViewEvent.OnStartGetMenu -> {
                menuListState.value = menuList()
            }

            is CompanyProfileViewEvent.OnMenuItemClick -> {
                editMenuState.value = Event(event.menuId)
            }
        }
    }

    private fun menuList() = listOf(
        CompanyMenu(
            id = MENU_CITY,
            title = UIResource.StringResource(R.string.city_list),
            desc = UIResource.StringResource(R.string.city_list_desc)
        ), CompanyMenu(
            id = MENU_BRAND,
            title = UIResource.StringResource(R.string.brand_list),
            desc = UIResource.StringResource(R.string.brand_list_desc)
        ), CompanyMenu(
            id = MENU_CLIENT,
            title = UIResource.StringResource(R.string.client_list),
            desc = UIResource.StringResource(R.string.client_list_desc)
        ), CompanyMenu(
            id = MENU_PRODUCT,
            title = UIResource.StringResource(R.string.product_list),
            desc = UIResource.StringResource(R.string.product_list_desc)
        )
    )
}