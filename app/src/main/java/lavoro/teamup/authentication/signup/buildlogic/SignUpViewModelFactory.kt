package lavoro.teamup.authentication.signup.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lavoro.teamup.authentication.signup.SignUpViewModel
import lavoro.teamup.data.repository.LoginRepository

class SignUpViewModelFactory(
    private val loginRepository: LoginRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            SignUpViewModel(loginRepository) as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}