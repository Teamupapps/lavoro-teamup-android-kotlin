package lavoro.teamup.brandlist

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
import lavoro.teamup.brandlist.adapter.BrandAdapter
import lavoro.teamup.brandlist.buildlogic.BrandViewInjector
import lavoro.teamup.core.binding.ViewBindingHolder
import lavoro.teamup.core.binding.ViewBindingHolderImpl
import lavoro.teamup.core.view.isQueryMatch
import lavoro.teamup.core.view.relaunchCurrentView
import lavoro.teamup.core.view.setInputMaxLength
import lavoro.teamup.core.view.setupListItemDecoration
import lavoro.teamup.core.view.toEditable
import lavoro.teamup.core.view.visible
import lavoro.teamup.data.model.brand.Brand
import lavoro.teamup.databinding.ViewDataExpandedListBinding
import lavoro.teamup.home.HomeActivityInteract

class BrandView : Fragment(), View.OnClickListener,
    ViewBindingHolder<ViewDataExpandedListBinding> by ViewBindingHolderImpl() {

    private lateinit var viewModel: BrandViewModel
    private lateinit var viewInteract: HomeActivityInteract
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var brandAdapter: BrandAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        destroyBinding()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.txt_update) viewModel.handleEvent(
            BrandViewEvent.OnUpdateTxtClick(
                brand = binding?.edTitle?.text?.trim().toString()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(ViewDataExpandedListBinding.inflate(layoutInflater), this@BrandView) {
        setupBottomSheetState()
        setupViewInteract()
        setupViewInputs()
        setupListAdapter()
        setupViewModel()
        viewModel.setupStatesObserver()
        setupClickListener()
    }

    private fun setupBottomSheetState() {
        binding?.apply {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetSubAction)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun setupViewInteract() {
        viewInteract = activity as HomeActivityInteract
    }

    private fun setupViewInputs() {
        binding?.apply {
            edDesc.visible(false)
            dataSpinner.visible(false)

            edTitle.hint = getString(R.string.brand)
            edTitle.setInputMaxLength(maxLength = resources.getInteger(R.integer.input_brand_max_length))

            itemSearch.mtSearchView.queryHint = getString(R.string.brands_search)

            txtUpdate.setOnClickListener(this@BrandView)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@BrandView,
            factory = BrandViewInjector(requireActivity().application).provideViewModelFactory()
        )[BrandViewModel::class.java]
        viewModel.handleEvent(BrandViewEvent.OnStartGetBrand)
    }

    private fun setupClickListener() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN)
                findNavController().popBackStack()
            else viewModel.handleEvent(
                BrandViewEvent.HideBottomSheet
            )
        }
    }

    private fun setupListAdapter() {
        brandAdapter = BrandAdapter()
        binding?.recDataList?.apply {
            adapter = brandAdapter
            setupListItemDecoration(context)
        }
        brandAdapter.event.observe(viewLifecycleOwner) {
            viewModel.handleEvent(it)
        }
    }

    private fun setupSearchViewListener(list: List<Brand>) {
        binding?.itemSearch?.mtSearchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList = mutableListOf<Brand>()

                    for (brand: Brand in list)
                        if (brand.name.isQueryMatch(it))
                            filteredList.add(brand)

                    if (filteredList.isNotEmpty()) brandAdapter.submitList(filteredList)
                }
                return true
            }
        })
    }

    private fun BrandViewModel.setupStatesObserver() {
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
        brand.observe(viewLifecycleOwner) {
            binding?.edTitle?.text = it.name.toEditable()
        }
        brandList.observe(viewLifecycleOwner) {
            brandAdapter.submitList(it)
            setupSearchViewListener(it)
        }
    }
}