package lavoro.teamup.productlist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lavoro.teamup.data.repository.BrandRepository
import lavoro.teamup.data.repository.ProductRepository
import lavoro.teamup.data.repository.preferences.PreferenceRepository
import lavoro.teamup.productlist.ProductViewModel

class ProductViewModelFactory(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val preferenceRepository: PreferenceRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(ProductViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            ProductViewModel(productRepository,brandRepository,preferenceRepository) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
