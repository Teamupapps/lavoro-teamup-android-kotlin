package lavoro.teamup.data.repository.preferences

interface TransactionPreferenceRepository {
    fun getUseClientEntry(): Boolean
    fun getUseProductEntry(): Boolean
}