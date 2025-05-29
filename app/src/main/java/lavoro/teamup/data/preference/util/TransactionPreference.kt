package lavoro.teamup.data.preference.util

interface TransactionPreference {
    fun displayTodayTransactionsOnly(): Boolean
    fun useClientEntry(): Boolean
    fun useProductEntry(): Boolean
}