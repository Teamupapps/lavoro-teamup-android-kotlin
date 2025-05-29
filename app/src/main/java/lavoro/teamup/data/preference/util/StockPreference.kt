package lavoro.teamup.data.preference.util

interface StockPreference {
    fun isStockUsed(): Boolean
    fun calculateTodayStockOnly(): Boolean
}
