package lavoro.teamup.data.repository

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.client.Client

interface ClientRepository {
    suspend fun getClientList(localOnly: Unit? = null): Result<Exception, List<Client>>
    suspend fun updateClient(client: Client): Result<Exception, Unit>
    suspend fun deleteClient(clientId: String): Result<Exception, Unit>
    suspend fun clearListCacheTime(): Result<Exception, Unit>

}
