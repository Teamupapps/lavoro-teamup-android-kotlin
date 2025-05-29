package lavoro.teamup.productlist

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
import lavoro.teamup.data.model.entry.BrandEntry
import lavoro.teamup.data.model.product.Product
import lavoro.teamup.data.repository.BrandRepository
import lavoro.teamup.data.repository.ProductRepository
import lavoro.teamup.data.repository.preferences.PreferenceRepository

class ProductViewModel(
    private val productRepository: ProductRepository,
    brandRepository: BrandRepository,
    preferenceRepository: PreferenceRepository,
) : BaseAdministrationViewModel<ProductViewEvent>(
    productRepository = productRepository,
    brandRepository = brandRepository,
    preferenceRepository = preferenceRepository,
    cityRepository = null,
    clientRepository = null,
) {

    private val productState = MutableLiveData<Product>()
    val product: LiveData<Product> get() = productState

    private val updateState = MutableLiveData<Unit>()
    val updated: LiveData<Unit> get() = updateState

    override fun handleEvent(event: ProductViewEvent) {
        when (event) {
            is ProductViewEvent.OnStartGetProduct -> {
                setupNewProduct()
                getProductList()
            }

            is ProductViewEvent.GetBrandList -> getBrandList()
            is ProductViewEvent.HideBottomSheet -> hideBottomSheet()
            is ProductViewEvent.OnListItemClick -> setupProduct(pos = event.pos)
            is ProductViewEvent.OnSpinnerBrandSelect -> updateBrandEntry(pos = event.pos)
            is ProductViewEvent.OnUpdateTxtClick -> updateProduct(event.product)
            is ProductViewEvent.OnMenuDeleteClick -> deleteProduct(event.pos)
            is ProductViewEvent.OnMenuRefreshClick -> clearListCacheTime()
        }
    }

    private fun setupNewProduct() = viewModelScope.launch {
        productState.value = Product("", "", BrandEntry())
        delay(DELAY_VIEW_EXPAND)
        expandBottomSheet()
    }

    private fun setupProduct(pos: Int) = viewModelScope.launch {
        productState.value = productListState.value?.get(pos)
        delay(DELAY_VIEW_EXPAND)
        expandBottomSheet()
    }

    private fun updateBrandEntry(pos: Int) {
        brandListState.value?.get(pos)?.let { brand ->
            productState.value?.brandEntry = BrandEntry(
                id = brand.id,
                name = brand.name
            )
        }
    }

    private fun clearListCacheTime() = viewModelScope.launch {
        if (productRepository.clearListCacheTime() is Result.Value) productUpdated()
        else showError(R.string.cannot_update_local_entries)
    }

    private fun deleteProduct(pos: Int) = viewModelScope.launch {
        productListState.value?.get(pos)?.id?.let {
            showLoading()

            when (val result = productRepository.deleteProduct(it)) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = { showError(R.string.cannot_update_entries) })
                is Result.Value -> productUpdated()
            }
            hideLoading()
        }
    }

    private fun updateProduct(productName: String) = viewModelScope.launch {
        productState.value?.let {

            if (productName.isValidLength(
                    minDig = MIN_TITLE_DIG,
                    maxDig = MAX_TITLE_DIG
                ) == false
            ) {
                showError(R.string.invalid_name)
                return@launch
            }

            if (it.id.isBlank())
                productState.value?.id = getSystemTimeMillis()

            showLoading()

            when (val result = productRepository.updateProduct(
                product = it.copy(name = productName)
            )) {
                is Result.Error -> result.error.message.actionExceptionMsg(error = {
                    showError(R.string.cannot_update_entries)
                })

                is Result.Value -> productUpdated()
            }
            hideLoading()
        }
    }

    private fun productUpdated() {
        updateState.value = Unit
    }

}