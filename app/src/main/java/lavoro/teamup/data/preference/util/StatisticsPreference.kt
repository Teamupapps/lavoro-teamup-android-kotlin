package lavoro.teamup.data.preference.util

interface StatisticsPreference {
    fun isStatisticsUsed(): Boolean
    fun calculateTodayStatisticsOnly(): Boolean
}
