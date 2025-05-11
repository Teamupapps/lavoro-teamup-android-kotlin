package lavoro.teamup.data.repository.preferences

interface AdvancedPreferenceRepository {
    fun getSuggestAddStatus(): Boolean
    fun getSuggestShareStatus(): Boolean
}