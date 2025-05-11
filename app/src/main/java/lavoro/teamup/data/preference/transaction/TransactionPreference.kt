package lavoro.teamup.data.preference.transaction

interface TransactionPreference {
    fun useClientEntry(): Boolean
    fun useProductEntry(): Boolean
}