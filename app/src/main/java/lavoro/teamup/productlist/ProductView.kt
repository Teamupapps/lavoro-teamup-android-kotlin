package lavoro.teamup.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import lavoro.teamup.R
import lavoro.teamup.core.binding.ViewBindingHolder
import lavoro.teamup.core.binding.ViewBindingHolderImpl
import lavoro.teamup.core.view.bindBrandList
import lavoro.teamup.core.view.isQueryMatch
import lavoro.teamup.core.view.relaunchCurrentView
import lavoro.teamup.core.view.selectQuery
import lavoro.teamup.core.view.setInputMaxLength
import lavoro.teamup.core.view.setupListItemDecoration
import lavoro.teamup.core.view.toEditable
import lavoro.teamup.core.view.visible
import lavoro.teamup.core.wrapper.EventObserver
import lavoro.teamup.data.model.product.Product
import lavoro.teamup.databinding.ViewDataExpandedListBinding
import lavoro.teamup.home.HomeActivityInteract
import lavoro.teamup.productlist.adapter.ProductAdapter
import lavoro.teamup.productlist.buildlogic.ProductViewInjector

class ProductView : Fragment(), View.OnClickListener,
    ViewBindingHolder<ViewDataExpandedListBinding> by ViewBindingHolderImpl() {

    private lateinit var viewInteract: HomeActivityInteract
    private lateinit var viewModel: ProductViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var productAdapter: ProductAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        destroyBinding()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.txt_update) viewModel.handleEvent(
            ProductViewEvent.OnUpdateTxtClick(
                product = binding?.edTitle?.text?.trim().toString()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(ViewDataExpandedListBinding.inflate(layoutInflater), this@ProductView) {
        setupBottomSheetState()
        setupViewInteract()
        setupViewInputs()
        setupListAdapter()
        setupViewModel()
        viewModel.setupStatesObserver()
        setupClickListener()
    }

    private fun setupViewInteract() {
        viewInteract = activity as HomeActivityInteract
    }

    private fun setupBottomSheetState() {
        binding?.apply {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetSubAction)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun setupViewInputs() {
        binding?.apply {

            edTitle.hint = getString(R.string.product)

            edTitle.setInputMaxLength(maxLength = resources.getInteger(R.integer.input_product_max_length))

            edDesc.visible(false)

            itemSearch.mtSearchView.queryHint = getString(R.string.products_search)

            txtUpdate.setOnClickListener(this@ProductView)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@ProductView,
            factory = ProductViewInjector(requireActivity().application).provideViewModelFactory()
        )[ProductViewModel::class.java]
        viewModel.handleEvent(ProductViewEvent.GetBrandList)
        viewModel.handleEvent(ProductViewEvent.OnStartGetProduct)
    }

    private fun setupSearchViewListener(list: List<Product>) {
        binding?.itemSearch?.mtSearchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList = mutableListOf<Product>()

                    for (product: Product in list)
                        if (product.name.isQueryMatch(it))
                            filteredList.add(product)
                        else if (product.brandEntry.name?.isQueryMatch(it) == true)
                            filteredList.add(product)

                    if (filteredList.isNotEmpty()) productAdapter.submitList(filteredList)
                }
                return true
            }
        })
    }

    private fun setupClickListener() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN)
                findNavController().popBackStack()
            else viewModel.handleEvent(
                ProductViewEvent.HideBottomSheet
            )
        }
    }

    private fun setupListAdapter() {
        productAdapter = ProductAdapter()
        binding?.recDataList?.apply {
            adapter = productAdapter
            setupListItemDecoration(context)
        }
        productAdapter.event.observe(viewLifecycleOwner) {
            viewModel.handleEvent(it)
        }
    }

    private fun bindProduct(product: Product) {
        binding?.apply {
            edTitle.text = product.name.toEditable()
            dataSpinner.selectQuery(query = product.brandEntry.name)
        }
    }

    private fun ProductViewModel.setupStatesObserver() {
        bottomSheetViewState.observe(viewLifecycleOwner) {
            bottomSheetBehavior.state = it
        }
        loading.observe(viewLifecycleOwner) {
            viewInteract.updateProgressLoad(it)
        }
        error.observe(viewLifecycleOwner) {
            viewInteract.displayToast(it.asString(context))
        }
        updated.observe(viewLifecycleOwner) {
            relaunchCurrentView()
        }
        product.observe(viewLifecycleOwner) {
            bindProduct(it)
        }
        productList.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
            setupSearchViewListener(it)
        }
        brandList.observe(viewLifecycleOwner) {
            binding?.dataSpinner?.bindBrandList(
                dataList = it,
                selectTitle = null,
                selectAction = { position ->
                    handleEvent(ProductViewEvent.OnSpinnerBrandSelect(position ?: 99))
                })
        }
        addBrandNavigateAttempt.observe(viewLifecycleOwner, EventObserver {
            viewInteract.displaySnack(
                text = getString(R.string.brand_list_empty),
                actionText = getString(R.string.update_item),
                action = {
                    view?.post {
                        if (findNavController().currentDestination?.id == R.id.productView) findNavController().navigate(
                            ProductViewDirections.navigateToBrand()
                        )
                    }
                }
            )
        })

    }
}