package lavoro.teamup.citylist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lavoro.teamup.citylist.CityViewModel
import lavoro.teamup.data.repository.CityRepository

class CityViewModelFactory(
    private val cityRepository: CityRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(CityViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            CityViewModel(cityRepository) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
