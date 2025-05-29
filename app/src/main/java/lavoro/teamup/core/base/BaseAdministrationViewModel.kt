package lavoro.teamup.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch
import lavoro.teamup.R
import lavoro.teamup.core.wrapper.Event
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.brand.Brand
import lavoro.teamup.data.model.city.City
import lavoro.teamup.data.model.client.Client
import lavoro.teamup.data.model.product.Product
import lavoro.teamup.data.repository.BrandRepository
import lavoro.teamup.data.repository.CityRepository
import lavoro.teamup.data.repository.ClientRepository
import lavoro.teamup.data.repository.ProductRepository
import lavoro.teamup.data.repository.preferences.PreferenceRepository

open class BaseAdministrationViewModel<VE>(
    private val cityRepository: CityRepository?,
    private val clientRepository: ClientRepository?,
    private val brandRepository: BrandRepository?,
    private val productRepository: ProductRepository?,
    private val preferenceRepository: PreferenceRepository?
) : BaseViewModel<VE>() {

    override fun handleEvent(event: VE) = Unit

    internal val bottomSheetViewState = MutableLiveData<Int>()

    internal val addCityNavigateAttempt = MutableLiveData<Event<Unit>>()
    internal val addClientNavigateAttempt = MutableLiveData<Event<Unit>>()
    internal val addBrandNavigateAttempt = MutableLiveData<Event<Unit>>()
    internal val addProductNavigateAttempt = MutableLiveData<Event<Unit>>()

    protected val cityListState = MutableLiveData<List<City>>()
    val cityList: LiveData<List<City>> get() = cityListState

    protected val clientListState = MutableLiveData<List<Client>>()
    val clientList: LiveData<List<Client>> get() = clientListState

    protected val brandListState = MutableLiveData<List<Brand>>()
    val brandList: LiveData<List<Brand>> get() = brandListState

    protected val productListState = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = productListState


    private fun isSuggestAddEnable() = preferenceRepository?.getSuggestAddStatus()

    protected fun isClientEntryEnable() = preferenceRepository?.getUseClientEntry()

    protected fun isProductEntryEnable() = preferenceRepository?.getUseProductEntry()

    protected fun isNoteEnable() = preferenceRepository?.getUseNoteStatus()

    protected fun isStatisticsEnable() = preferenceRepository?.getUseStaticsStatus()

    protected fun isStockEnable() = preferenceRepository?.getUseStockStatus()

    protected fun isBalanceEnable() = preferenceRepository?.getUseBalanceStatus()

    private fun getLocalCityList() = viewModelScope.launch {
        val result = cityRepository?.getCityList(localOnly = Unit)
        if (result is Result.Value) cityListState.value =
            result.value.asReversed()
        else showError(R.string.city_list_error)
    }

    private fun getLocalClientList() = viewModelScope.launch {
        val result = clientRepository?.getClientList(localOnly = Unit)
        if (result is Result.Value) clientListState.value = result.value.asReversed()
        else showError(R.string.client_list_error)
    }

    private fun getLocalBrandList() = viewModelScope.launch {
        val result = brandRepository?.getBrandList(localOnly = Unit)
        if (result is Result.Value) brandListState.value = result.value.asReversed()
        else showError(R.string.brand_list_error)
    }

    private fun getLocalProductList() = viewModelScope.launch {
        val result = productRepository?.getProductList(localOnly = Unit)
        if (result is Result.Value) productListState.value =
            result.value.asReversed()
        else showError(R.string.product_list_error)
    }

    protected fun getCityList() = viewModelScope.launch {
        if (cityRepository == null) {
            showError(R.string.city_list_error)
            return@launch
        }
        showLoading()
        when (val result = cityRepository.getCityList()) {
            is Result.Error -> result.error.message.actionExceptionMsg(
                offline = {
                    getLocalCityList()
                },
                unauthorised = { Unit },
                deactivated = { Unit },
                error = {
                    showError(R.string.city_list_error)
                }
            )

            is Result.Value -> {
                cityListState.value = result.value.asReversed()

                if (cityListState.value?.isEmpty() == true && isSuggestAddEnable() == true) addCityNavigateAttempt.value =
                    Event(Unit)

            }
        }
        hideLoading()
    }

    protected fun getClientList() = viewModelScope.launch {
        if (clientRepository == null) {
            showError(R.string.client_list_error)
            return@launch
        }
        showLoading()
        when (val result = clientRepository.getClientList()) {
            is Result.Error -> result.error.message.actionExceptionMsg(
                offline = {
                    getLocalClientList()
                },
                unauthorised = { Unit },
                deactivated = { Unit },
                error = {
                    showError(R.string.client_list_error)
                }
            )

            is Result.Value -> {
                clientListState.value = result.value.asReversed()

                if (clientListState.value?.isEmpty() == true && isSuggestAddEnable() == true) addClientNavigateAttempt.value =
                    Event(Unit)
            }
        }
        hideLoading()
    }

    protected fun getBrandList() = viewModelScope.launch {
        if (brandRepository == null) {
            showError(R.string.brand_list_error)
            return@launch
        }
        showLoading()
        when (val result = brandRepository.getBrandList()) {
            is Result.Error -> result.error.message.actionExceptionMsg(
                offline = {
                    getLocalBrandList()
                },
                unauthorised = { Unit },
                deactivated = { Unit },
                error = {
                    showError(R.string.brand_list_error)
                }
            )

            is Result.Value -> {
                brandListState.value = result.value.asReversed()

                if (brandListState.value?.isEmpty() == true && isSuggestAddEnable() == true) addBrandNavigateAttempt.value =
                    Event(Unit)

            }
        }
        hideLoading()
    }

    protected fun getProductList() = viewModelScope.launch {
        if (productRepository == null) {
            showError(R.string.product_list_error)
            return@launch
        }
        showLoading()
        when (val result = productRepository.getProductList()) {
            is Result.Error -> result.error.message.actionExceptionMsg(
                offline = {
                    getLocalProductList()
                },
                unauthorised = { Unit },
                deactivated = { Unit },
                error = {
                    showError(R.string.product_list_error)
                }
            )

            is Result.Value -> {
                productListState.value = result.value.asReversed()

                if (productListState.value?.isEmpty() == true && isSuggestAddEnable() == true) addProductNavigateAttempt.value =
                    Event(Unit)
            }
        }
        hideLoading()
    }

    protected fun expandBottomSheet() {
        bottomSheetViewState.value = BottomSheetBehavior.STATE_EXPANDED
    }

    protected fun hideBottomSheet() {
        bottomSheetViewState.value = BottomSheetBehavior.STATE_HIDDEN
    }
}