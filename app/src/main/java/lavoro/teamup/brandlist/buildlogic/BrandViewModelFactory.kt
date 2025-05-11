package lavoro.teamup.brandlist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lavoro.teamup.brandlist.BrandViewModel
import lavoro.teamup.data.repository.BrandRepository

class BrandViewModelFactory(
    private val brandRepository: BrandRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(BrandViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            BrandViewModel(brandRepository) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
