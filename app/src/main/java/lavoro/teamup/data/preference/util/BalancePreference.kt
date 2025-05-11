package lavoro.teamup.data.preference.util

interface BalancePreference {
    fun isBalanceUsed(): Boolean
    fun calculateTodayBalanceOnly(): Boolean
}
