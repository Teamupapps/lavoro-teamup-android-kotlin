package lavoro.teamup.data.repository

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.brand.Brand

interface BrandRepository {
    suspend fun getBrandList(localOnly: Unit? = null): Result<Exception, List<Brand>>
    suspend fun updateBrand(brand: Brand): Result<Exception, Unit>
    suspend fun deleteBrand(brandId: String): Result<Exception, Unit>
    suspend fun clearListCacheTime(): Result<Exception, Unit>
}
