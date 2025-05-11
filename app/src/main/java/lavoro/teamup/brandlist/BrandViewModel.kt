package lavoro.teamup.brandlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lavoro.teamup.R
import lavoro.teamup.core.DELAY_VIEW_EXPAND
import lavoro.teamup.core.base.BaseAdministrationViewModel
import lavoro.teamup.core.getSystemTimeMillis
import lavoro.teamup.core.view.MAX_TITLE_DIG
import lavoro.teamup.core.view.MIN_TITLE_DIG
import lavoro.teamup.core.view.isValidLength
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.brand.Brand
import lavoro.teamup.data.repository.BrandRepository

class BrandViewModel(
    private val brandRepository: BrandRepository
) : BaseAdministrationViewModel<BrandViewEvent>(
    brandRepository = brandRepository,
    productRepository = null,
    cityRepository = null,
    clientRepository = null,
    preferenceRepository = null
) {

    private val brandState = MutableLiveData<Brand>()
    val brand: LiveData<Brand> get() = brandState

    private val updateState = MutableLiveData<Unit>()
    val updated: LiveData<Unit> get() = updateState

    override fun handleEvent(event: BrandViewEvent) {
        when (event) {
            is BrandViewEvent.OnStartGetBrand -> {
                setupNewBrand()
                getBrandList()
            }

            is BrandViewEvent.HideBottomSheet -> hideBottomSheet()
            is BrandViewEvent.OnMenuDeleteClick -> deleteBrand(event.pos)
            is BrandViewEvent.OnMenuRefreshClick -> clearListCacheTime()
            is BrandViewEvent.OnListItemClick -> setupBrand(event.pos)
            is BrandViewEvent.OnUpdateTxtClick -> updateBrand(event.brand)
        }
    }

    private fun setupNewBrand() = viewModelScope.launch {
        brandState.value = Brand("", "")
        delay(DELAY_VIEW_EXPAND)
        expandBottomSheet()
    }

    private fun setupBrand(pos: Int) = viewModelScope.launch {
        brandState.value = brandListState.value?.get(pos)
        delay(DELAY_VIEW_EXPAND)
        expandBottomSheet()
    }

    private fun clearListCacheTime() = viewModelScope.launch {
        if (brandRepository.clearListCacheTime() is Result.Value) brandUpdated()
        else showError(R.string.cannot_update_local_entries)
    }

    private fun deleteBrand(pos: Int) = viewModelScope.launch {
        brandListState.value?.get(pos)?.id?.let {
            showLoading()
            when (val result = brandRepository.deleteBrand(it)) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = { showError(R.string.cannot_update_entries) })
                is Result.Value -> brandUpdated()
            }
            hideLoading()
        }
    }

    private fun updateBrand(brand: String) = viewModelScope.launch {
        brandState.value?.let {

            if (brand.isValidLength(minDig = MIN_TITLE_DIG, maxDig = MAX_TITLE_DIG) == false) {
                showError(R.string.invalid_name)
                return@launch
            }

            if (it.id.isBlank()) {
                brandState.value?.id = getSystemTimeMillis()
            }

            showLoading()

            when (val result = brandRepository.updateBrand(
                brand = it.copy(name = brand)
            )) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = {
                    showError(R.string.cannot_update_entries)
                })

                is Result.Value -> brandUpdated()
            }

            hideLoading()
        }
    }

    private fun brandUpdated() {
        updateState.value = Unit
    }
}