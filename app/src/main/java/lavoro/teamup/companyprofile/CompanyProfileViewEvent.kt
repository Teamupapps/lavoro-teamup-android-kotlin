package lavoro.teamup.companyprofile

sealed class CompanyProfileViewEvent {
    data object OnStartGetMenu : CompanyProfileViewEvent()
    data class OnMenuItemClick(val menuId: Int) : CompanyProfileViewEvent()
}
