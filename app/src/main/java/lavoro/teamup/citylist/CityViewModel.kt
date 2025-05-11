package lavoro.teamup.citylist

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
import lavoro.teamup.data.model.city.City
import lavoro.teamup.data.repository.CityRepository

class CityViewModel(
    private val cityRepository: CityRepository
) : BaseAdministrationViewModel<CityViewEvent>(
    cityRepository = cityRepository,
    clientRepository = null,
    brandRepository = null,
    productRepository = null,
    preferenceRepository = null
) {

    private val cityState = MutableLiveData<City>()
    val city: LiveData<City> get() = cityState

    private val updateState = MutableLiveData<Unit>()
    val updated: LiveData<Unit> get() = updateState

    override fun handleEvent(event: CityViewEvent) {
        when (event) {
            is CityViewEvent.OnStartGetCity -> {
                setupNewCity()
                getCityList()
            }

            is CityViewEvent.OnListItemClick -> setupCity(pos = event.pos)
            is CityViewEvent.HideBottomSheet -> hideBottomSheet()
            is CityViewEvent.OnUpdateTxtClick -> updateCity(event.name)
            is CityViewEvent.OnMenuDeleteClick -> deleteCity(event.pos)
            is CityViewEvent.OnMenuRefreshClick -> clearListCacheTime()
        }
    }

    private fun setupNewCity() = viewModelScope.launch {
        cityState.value = City("", "")
        delay(DELAY_VIEW_EXPAND)
        expandBottomSheet()
    }

    private fun setupCity(pos: Int) = viewModelScope.launch {
        cityState.value = cityListState.value?.get(pos)
        delay(DELAY_VIEW_EXPAND)
        expandBottomSheet()
    }

    private fun clearListCacheTime() = viewModelScope.launch {
        if (cityRepository.clearListCacheTime() is Result.Value) cityUpdated()
        else showError(R.string.cannot_update_local_entries)
    }

    private fun deleteCity(pos: Int) = viewModelScope.launch {
        cityListState.value?.get(pos)?.id?.let {
            showLoading()
            when (val result = cityRepository.deleteCity(it)) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = { showError(R.string.cannot_update_entries) })
                is Result.Value -> cityUpdated()
            }
            hideLoading()
        }
    }

    private fun updateCity(cityName: String) = viewModelScope.launch {
        cityState.value?.let {

            if (cityName.isValidLength(minDig = MIN_TITLE_DIG, maxDig = MAX_TITLE_DIG) == false) {
                showError(R.string.invalid_name)
                return@launch
            }

            if (it.id.isBlank()) cityState.value?.id = getSystemTimeMillis()

            showLoading()

            when (val result = cityRepository.updateCity(
                city = it.copy(name = cityName)
            )) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = {
                    showError(R.string.cannot_update_entries)
                })

                is Result.Value -> {
                    cityUpdated()
                }
            }

            hideLoading()
        }
    }

    private fun cityUpdated() {
        updateState.value = Unit
    }
}