package lavoro.teamup.companyprofile.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lavoro.teamup.companyprofile.CompanyProfileViewModel

class CompanyProfileViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(CompanyProfileViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            CompanyProfileViewModel() as T
        else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
