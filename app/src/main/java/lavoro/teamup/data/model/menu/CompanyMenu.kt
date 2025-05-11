package lavoro.teamup.data.model.menu

import lavoro.teamup.core.wrapper.UIResource

data class CompanyMenu(
    val id: Int,
    val title: UIResource,
    val desc: UIResource,
    val isNav: Boolean = true,
    val isColored: Boolean = false,
    val isSingleTitle: Boolean = false
)
