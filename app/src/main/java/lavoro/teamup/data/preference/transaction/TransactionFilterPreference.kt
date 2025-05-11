package lavoro.teamup.data.preference.transaction

interface TransactionFilterPreference {
    fun filterListBySellOnly(): Boolean
    fun filterListWithinMonthOnly(): Boolean
}