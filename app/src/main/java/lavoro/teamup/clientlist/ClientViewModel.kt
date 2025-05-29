package lavoro.teamup.clientlist

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
import lavoro.teamup.core.view.isPhoneNumberValid
import lavoro.teamup.core.view.isValidLength
import lavoro.teamup.core.wrapper.Event
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.client.Client
import lavoro.teamup.data.model.entry.CityEntry
import lavoro.teamup.data.repository.CityRepository
import lavoro.teamup.data.repository.ClientRepository
import lavoro.teamup.data.repository.preferences.PreferenceRepository

class ClientViewModel(
    private val clientRepository: ClientRepository,
    cityRepository: CityRepository,
    preferenceRepository: PreferenceRepository,
) : BaseAdministrationViewModel<ClientViewEvent>(
    cityRepository = cityRepository,
    clientRepository = clientRepository,
    preferenceRepository = preferenceRepository,
    brandRepository = null,
    productRepository = null,
) {
    internal val dialClientAttempt = MutableLiveData<Event<String>>()

    private val clientState = MutableLiveData<Client>()
    val client: LiveData<Client> get() = clientState

    private val updateState = MutableLiveData<Unit>()
    val updated: LiveData<Unit> get() = updateState

    override fun handleEvent(event: ClientViewEvent) {
        when (event) {
            is ClientViewEvent.OnStartGetClient -> {
                setupNewClient()
                getClientList()
            }

            is ClientViewEvent.OnListItemClick -> setupClient(pos = event.pos)
            is ClientViewEvent.GetCityList -> getCityList()
            is ClientViewEvent.HideBottomSheet -> hideBottomSheet()
            is ClientViewEvent.OnSpinnerCitySelect -> updateCityEntry(event.pos)
            is ClientViewEvent.OnUpdateTxtClick -> updateClient(event.name, event.phone)
            is ClientViewEvent.OnMenuDeleteClick -> deleteClient(event.pos)
            is ClientViewEvent.OnMenuRefreshClick -> clearListCacheTime()
            is ClientViewEvent.OnMenuDialClick -> dialClient(event.pos)

        }
    }

    private fun setupNewClient() = viewModelScope.launch {
        clientState.value = Client("", "", "", CityEntry())
        delay(DELAY_VIEW_EXPAND)
        expandBottomSheet()
    }

    private fun setupClient(pos: Int) = viewModelScope.launch {
        clientState.value = clientListState.value?.get(pos)
        delay(DELAY_VIEW_EXPAND)
        expandBottomSheet()
    }

    private fun clearListCacheTime() = viewModelScope.launch {
        if (clientRepository.clearListCacheTime() is Result.Value) clientUpdated()
        else showError(R.string.cannot_update_local_entries)
    }

    private fun deleteClient(pos: Int) = viewModelScope.launch {
        clientListState.value?.get(pos)?.id?.let {

            showLoading()

            when (val result = clientRepository.deleteClient(it)) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = { showError(R.string.cannot_update_entries) })
                is Result.Value -> clientUpdated()
            }
            hideLoading()
        }
    }

    private fun updateClient(name: String, phone: String) = viewModelScope.launch {
        clientState.value?.let {

            if (name.isValidLength(minDig = MIN_TITLE_DIG, maxDig = MAX_TITLE_DIG) == false) {
                showError(R.string.invalid_name)
                return@launch
            }

            if (it.id.isBlank()) it.id = getSystemTimeMillis()

            showLoading()

            when (val result = clientRepository.updateClient(
                client = it.copy(
                    name = name, phone = phone
                )
            )) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = {
                    showError(R.string.cannot_update_entries)
                })

                is Result.Value -> {
                    clientUpdated()
                }
            }
            hideLoading()
        }
    }

    private fun updateCityEntry(pos: Int) {
        cityListState.value?.get(pos)?.let { city ->
            clientState.value?.cityEntry = CityEntry(
                id = city.id,
                name = city.name
            )
        }
    }

    private fun dialClient(pos: Int) {
        clientListState.value?.get(pos)?.let {
            if (it.phone.isPhoneNumberValid() == false) {
                showError(R.string.invalid_phone_num)
                return@let
            }
            dialClientAttempt.value = Event(it.phone)
        }
    }

    private fun clientUpdated() {
        updateState.value = Unit
    }

}

