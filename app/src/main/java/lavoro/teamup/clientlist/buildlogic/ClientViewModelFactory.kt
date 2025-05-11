package lavoro.teamup.clientlist.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lavoro.teamup.clientlist.ClientViewModel
import lavoro.teamup.data.repository.CityRepository
import lavoro.teamup.data.repository.ClientRepository
import lavoro.teamup.data.repository.preferences.PreferenceRepository

class ClientViewModelFactory(
    private val clientRepository: ClientRepository,
    private val cityRepository: CityRepository,
    private val preferenceRepository: PreferenceRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(ClientViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            ClientViewModel(clientRepository, cityRepository, preferenceRepository) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
