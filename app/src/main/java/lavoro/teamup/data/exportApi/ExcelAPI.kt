package lavoro.teamup.data.exportApi

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.transaction.Transaction


interface ExcelAPI {
    suspend fun buildTransactionListFile(list: List<Transaction>): Result<Exception, Unit>
}