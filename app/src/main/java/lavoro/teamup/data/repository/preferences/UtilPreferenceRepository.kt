package lavoro.teamup.data.repository.preferences

interface UtilPreferenceRepository {
    fun getUseStaticsStatus(): Boolean
    fun getUseBalanceStatus(): Boolean
    fun getUseStockStatus(): Boolean
    fun getUseNoteStatus(): Boolean
}