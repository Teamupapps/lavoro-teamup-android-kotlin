package lavoro.teamup.data.model.balance

import lavoro.teamup.core.wrapper.UIResource

data class TransactionBalance(
    var id: Int,
    var total: Double,
    var title: UIResource,
    var isCost: Boolean,
    var isSell: Boolean
)
