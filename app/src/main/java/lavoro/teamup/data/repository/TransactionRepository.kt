package lavoro.teamup.data.repository

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.stock.TransactionStock
import lavoro.teamup.data.model.balance.TransactionBalance
import lavoro.teamup.data.model.entry.AssetEntry
import lavoro.teamup.data.model.entry.ProductEntry
import lavoro.teamup.data.model.statics.TransactionStatics
import lavoro.teamup.data.model.transaction.Transaction

interface TransactionRepository {
    suspend fun getItemList(localOnly: Unit? = null): Result<Exception, List<Transaction>>

    suspend fun getItemById(itemId: String): Result<Exception, Transaction>

    suspend fun getLatestBuyAssetEntry(productEntry: ProductEntry? = null): Result<Exception, AssetEntry>

    suspend fun updateItem(transaction: Transaction): Result<Exception, Unit>

    suspend fun deleteItem(transactionId: String): Result<Exception, Unit>

    suspend fun getTransactionStock(): Result<Exception, List<TransactionStock>>

    suspend fun getTransactionBalance(): Result<Exception, List<TransactionBalance>>

    suspend fun getTransactionStatics(): Result<Exception, List<TransactionStatics>>

    suspend fun filterItemList(item: Transaction): Result<Exception, List<Transaction>>

    suspend fun calculateBalance(list: List<Transaction>): Result<Exception, List<TransactionBalance>>

    suspend fun getBalanceHintTitle(): Result<Exception, IntArray>

    suspend fun getStaticsHintTitle(): Result<Exception, IntArray>

    suspend fun getTransactionListHintTitle(): Result<Exception, IntArray>

    suspend fun getStockListHintTitle(): Result<Exception, IntArray>

    suspend fun getFilterListHintTitle(): Result<Exception, IntArray>

    suspend fun clearListCacheTime(): Result<Exception, Unit>

}