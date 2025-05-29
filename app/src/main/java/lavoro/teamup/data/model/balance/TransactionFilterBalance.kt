package lavoro.teamup.data.model.balance

import lavoro.teamup.core.wrapper.UIResource

data class TransactionFilterBalance(
    var id: String,
    var total: Double,
    var title: UIResource,
    var isSell:Boolean
)
