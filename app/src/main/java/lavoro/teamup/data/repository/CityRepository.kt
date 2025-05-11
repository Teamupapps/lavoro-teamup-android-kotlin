package lavoro.teamup.data.repository

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.city.City

interface CityRepository {
    suspend fun getCityList(localOnly: Unit? = null): Result<Exception, List<City>>
    suspend fun updateCity(city: City): Result<Exception, Unit>
    suspend fun deleteCity(cityId: String): Result<Exception, Unit>
    suspend fun clearListCacheTime(): Result<Exception, Unit>

}
