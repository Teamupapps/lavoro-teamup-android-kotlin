package lavoro.teamup.data.repository

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.balance.TransactionBalance
import lavoro.teamup.data.model.balance.TransactionFilterBalance
import lavoro.teamup.data.model.entry.AssetEntry
import lavoro.teamup.data.model.entry.ProductEntry
import lavoro.teamup.data.model.statics.TransactionStatics
import lavoro.teamup.data.model.stock.TransactionStock
import lavoro.teamup.data.model.transaction.Transaction

interface TransactionRepository {

    suspend fun getStatisticsHintTitle(): Result<Exception, IntArray>

    suspend fun getBalanceHintTitle(): Result<Exception, IntArray>

    suspend fun getStockHintTitle(): Result<Exception, IntArray>

    suspend fun getFilteredListHintTitle(): Result<Exception, IntArray>

    suspend fun getTransactionListHintTitle(): Result<Exception, IntArray>

    suspend fun getTransactionList(localOnly: Unit? = null): Result<Exception, List<Transaction>>

    suspend fun getTransactionById(itemId: String): Result<Exception, Transaction>

    suspend fun updateItem(transaction: Transaction): Result<Exception, Unit>

    suspend fun deleteItem(transactionId: String): Result<Exception, Unit>

    suspend fun filterTransactionList(item: Transaction): Result<Exception, List<Transaction>>

    suspend fun getTransactionStock(): Result<Exception, List<TransactionStock>>

    suspend fun getTransactionBalance(): Result<Exception, List<TransactionBalance>>

    suspend fun getTransactionStatics(): Result<Exception, List<TransactionStatics>>

    suspend fun getPreviousBuyAssetEntryByProduct(productEntry: ProductEntry? = null): Result<Exception, AssetEntry>

    suspend fun calculateBalance(list: List<Transaction>): Result<Exception, List<TransactionFilterBalance>>

    suspend fun clearListCacheTime(): Result<Exception, Unit>

}