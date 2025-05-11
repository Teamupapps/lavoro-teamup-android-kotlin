package lavoro.teamup.home.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import lavoro.teamup.data.repository.UserRepository
import lavoro.teamup.home.HomeActivityViewModel

class HomeActivityViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            HomeActivityViewModel(userRepository) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
