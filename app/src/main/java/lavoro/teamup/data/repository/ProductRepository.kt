package lavoro.teamup.data.repository

import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.product.Product

interface ProductRepository {
    suspend fun getProductList(localOnly: Unit? = null): Result<Exception, List<Product>>
    suspend fun updateProduct(product: Product): Result<Exception, Unit>
    suspend fun deleteProduct(productId: String): Result<Exception, Unit>
    suspend fun clearListCacheTime(): Result<Exception, Unit>

}
