package lavoro.teamup.authentication.login.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lavoro.teamup.authentication.login.LoginViewModel
import lavoro.teamup.data.repository.LoginRepository
import lavoro.teamup.data.repository.UserRepository

class LoginViewModelFactory(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            LoginViewModel(loginRepository, userRepository) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
