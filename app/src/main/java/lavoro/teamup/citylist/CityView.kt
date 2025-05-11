package lavoro.teamup.citylist

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
import lavoro.teamup.citylist.adapter.CityAdapter
import lavoro.teamup.citylist.buildlogic.CityViewInjector
import lavoro.teamup.core.binding.ViewBindingHolder
import lavoro.teamup.core.binding.ViewBindingHolderImpl
import lavoro.teamup.core.view.isQueryMatch
import lavoro.teamup.core.view.relaunchCurrentView
import lavoro.teamup.core.view.setupListItemDecoration
import lavoro.teamup.core.view.toEditable
import lavoro.teamup.core.view.visible
import lavoro.teamup.core.wrapper.EventObserver
import lavoro.teamup.data.model.city.City
import lavoro.teamup.databinding.ViewDataExpandedListBinding
import lavoro.teamup.home.HomeActivityInteract

class CityView : Fragment(),
    View.OnClickListener,
    ViewBindingHolder<ViewDataExpandedListBinding> by ViewBindingHolderImpl() {

    private lateinit var viewInteract: HomeActivityInteract
    private lateinit var viewModel: CityViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var cityAdapter: CityAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        destroyBinding()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.txt_update)
            viewModel.handleEvent(
                CityViewEvent.OnUpdateTxtClick(
                    name = binding?.edTitle?.text?.trim().toString()
                )
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(ViewDataExpandedListBinding.inflate(layoutInflater), this@CityView) {
        setupViewInteract()
        setupBottomSheetState()
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
            edDesc.visible(false)
            dataSpinner.visible(false)

            edTitle.hint = getString(R.string.city)

            itemSearch.mtSearchView.queryHint = getString(R.string.city_search)

            txtUpdate.setOnClickListener(this@CityView)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@CityView,
            factory = CityViewInjector(requireActivity().application).provideViewModelFactory()
        )[CityViewModel::class.java]
        viewModel.handleEvent(CityViewEvent.OnStartGetCity)
    }

    private fun setupSearchViewListener(list: List<City>) {
        binding?.itemSearch?.mtSearchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList = mutableListOf<City>()

                    for (city: City in list) if (city.name.isQueryMatch(it)) filteredList.add(city)

                    if (filteredList.isNotEmpty()) cityAdapter.submitList(filteredList)
                }
                return true
            }
        })
    }

    private fun setupClickListener() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN)
                findNavController().popBackStack()
            else viewModel.handleEvent(CityViewEvent.HideBottomSheet)
        }
    }

    private fun setupListAdapter() {
        cityAdapter = CityAdapter()
        binding?.recDataList?.apply {
            adapter = cityAdapter
            setupListItemDecoration(context)
        }
        cityAdapter.event.observe(viewLifecycleOwner) {
            viewModel.handleEvent(it)
        }
    }

    private fun CityViewModel.setupStatesObserver() {
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
        city.observe(viewLifecycleOwner) {
            binding?.edTitle?.text = it.name.toEditable()
        }
        cityList.observe(viewLifecycleOwner) {
            cityAdapter.submitList(it)
            setupSearchViewListener(it)
        }
    }
}