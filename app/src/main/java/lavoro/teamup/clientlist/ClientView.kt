package lavoro.teamup.clientlist

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
import lavoro.teamup.clientlist.adapter.ClientAdapter
import lavoro.teamup.clientlist.buildlogic.ClientViewInjector
import lavoro.teamup.core.binding.ViewBindingHolder
import lavoro.teamup.core.binding.ViewBindingHolderImpl
import lavoro.teamup.core.view.bindCityList
import lavoro.teamup.core.view.isQueryMatch
import lavoro.teamup.core.view.relaunchCurrentView
import lavoro.teamup.core.view.selectQuery
import lavoro.teamup.core.view.setInputMaxLength
import lavoro.teamup.core.view.setPhoneNumInput
import lavoro.teamup.core.view.setupListItemDecoration
import lavoro.teamup.core.view.toEditable
import lavoro.teamup.core.wrapper.EventObserver
import lavoro.teamup.data.model.client.Client
import lavoro.teamup.databinding.ViewDataExpandedListBinding
import lavoro.teamup.home.HomeActivityInteract

class ClientView : Fragment(),
    View.OnClickListener,
    ViewBindingHolder<ViewDataExpandedListBinding> by ViewBindingHolderImpl() {

    private lateinit var viewInteract: HomeActivityInteract
    private lateinit var viewModel: ClientViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var clientAdapter: ClientAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        destroyBinding()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.txt_update) viewModel.handleEvent(
            ClientViewEvent.OnUpdateTxtClick(
                name = binding?.edTitle?.text?.trim().toString(),
                phone = binding?.edDesc?.text?.trim().toString()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(ViewDataExpandedListBinding.inflate(layoutInflater), this@ClientView) {
        setupBottomSheet()
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

    private fun setupBottomSheet() {
        binding?.apply {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetSubAction)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }


    private fun setupViewInputs() {
        binding?.apply {
            edTitle.hint = getString(R.string.client)

            edDesc.hint = getString(R.string.phone_num)
            edDesc.setInputMaxLength(resources.getInteger(R.integer.input_phone_max_length))
            edDesc.setPhoneNumInput()

            itemSearch.mtSearchView.queryHint = getString(R.string.clients_search)

            txtUpdate.setOnClickListener(this@ClientView)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@ClientView,
            factory = ClientViewInjector(requireActivity().application).provideViewModelFactory()
        )[ClientViewModel::class.java]
        viewModel.handleEvent(ClientViewEvent.OnStartGetClient)
        viewModel.handleEvent(ClientViewEvent.GetCityList)
    }

    private fun setupClickListener() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN)
                findNavController().popBackStack()
            else viewModel.handleEvent(
                ClientViewEvent.HideBottomSheet
            )
        }
    }

    private fun setupListAdapter() {
        clientAdapter = ClientAdapter()
        binding?.recDataList?.apply {
            adapter = clientAdapter
            setupListItemDecoration(context)
        }
        clientAdapter.event.observe(
            viewLifecycleOwner
        ) {
            viewModel.handleEvent(it)
        }
    }

    private fun setupSearchViewListener(list: List<Client>) {
        binding?.itemSearch?.mtSearchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList = mutableListOf<Client>()

                    for (client: Client in list)
                        if (client.name.isQueryMatch(it)) filteredList.add(client)
                        else if (client.phone.isQueryMatch(it)) filteredList.add(client)
                        else if (client.cityEntry.name?.isQueryMatch(it) == true)
                            filteredList.add(client)

                    if (filteredList.isNotEmpty()) clientAdapter.submitList(filteredList)

                }
                return true
            }
        })
    }

    private fun bindClient(client: Client) {
        binding?.apply {
            edTitle.text = client.name.toEditable()

            edDesc.text = client.phone.toEditable()

            dataSpinner.selectQuery(query = client.cityEntry.name)
        }
    }

    private fun ClientViewModel.setupStatesObserver() {
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
        dialClientAttempt.observe(viewLifecycleOwner, EventObserver {
            viewInteract.actionDial(it)
        })
        client.observe(viewLifecycleOwner) {
            bindClient(it)
        }
        clientList.observe(viewLifecycleOwner) {
            clientAdapter.submitList(it)
            setupSearchViewListener(it)
        }
        cityList.observe(viewLifecycleOwner) {
            binding?.dataSpinner?.bindCityList(
                dataList = it,
                selectTitle = null,
                selectAction = { position ->
                    handleEvent(ClientViewEvent.OnSpinnerCitySelect(position ?: 99))
                })
        }
        addCityNavigateAttempt.observe(viewLifecycleOwner, EventObserver {
            viewInteract.displaySnack(
                text = getString(R.string.city_list_empty),
                actionText = getString(R.string.update_item),
                action = {
                    view?.post {
                        if (findNavController().currentDestination?.id == R.id.clientView) findNavController().navigate(
                            ClientViewDirections.navigateToCity()
                        )
                    }
                }
            )
        })
    }
}