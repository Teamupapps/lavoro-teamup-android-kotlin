package lavoro.teamup.companyprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import lavoro.teamup.R
import lavoro.teamup.companyprofile.adapter.CompanyMenuAdapter
import lavoro.teamup.companyprofile.buildlogic.CompanyProfileViewInjector
import lavoro.teamup.core.MENU_BRAND
import lavoro.teamup.core.MENU_CITY
import lavoro.teamup.core.MENU_CLIENT
import lavoro.teamup.core.MENU_PRODUCT
import lavoro.teamup.core.binding.ViewBindingHolder
import lavoro.teamup.core.binding.ViewBindingHolderImpl
import lavoro.teamup.core.view.setupListItemDecoration
import lavoro.teamup.core.wrapper.EventObserver
import lavoro.teamup.databinding.ViewCompanyProfileListBinding
import lavoro.teamup.home.HomeActivityInteract

class CompanyProfileView : Fragment(),
    ViewBindingHolder<ViewCompanyProfileListBinding> by ViewBindingHolderImpl() {

    private lateinit var viewInteract: HomeActivityInteract
    private lateinit var viewModel: CompanyProfileViewModel
    private lateinit var menuAdapter: CompanyMenuAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        destroyBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        initBinding(
            ViewCompanyProfileListBinding.inflate(layoutInflater),
            this@CompanyProfileView
        ) {
            setupViewInteract()
            setupMenuAdapter()
            setupViewModel()
            viewModel.startObserving()
        }

    private fun setupViewInteract() {
        viewInteract = activity as HomeActivityInteract
    }

    private fun setupMenuAdapter() {
        menuAdapter = CompanyMenuAdapter()
        binding?.recMenuList?.apply {
            setupListItemDecoration(context)
            adapter = menuAdapter
        }
        menuAdapter.event.observe(viewLifecycleOwner) {
            viewModel.handleEvent(it)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@CompanyProfileView,
            factory = CompanyProfileViewInjector(requireActivity().application).provideViewModelFactory()
        )[CompanyProfileViewModel::class.java]
        viewModel.handleEvent(CompanyProfileViewEvent.OnStartGetMenu)
    }

    private fun isDestination(): Boolean =
        findNavController().currentDestination?.id == R.id.companyProfileView

    private fun CompanyProfileViewModel.startObserving() {
        loading.observe(viewLifecycleOwner) {
            viewInteract.updateProgressLoad(it)
        }
        error.observe(viewLifecycleOwner) {
            viewInteract.displayToast(it.asString(context))
        }
        menuList.observe(viewLifecycleOwner) {
            menuAdapter.submitList(it)
        }
        editMenu.observe(viewLifecycleOwner, EventObserver {
            if (isDestination()) when (it) {
                MENU_CITY -> findNavController().navigate(CompanyProfileViewDirections.navigateToCity())
                MENU_CLIENT -> findNavController().navigate(CompanyProfileViewDirections.navigateToClient())
                MENU_BRAND -> findNavController().navigate(CompanyProfileViewDirections.navigateToBrand())
                MENU_PRODUCT -> findNavController().navigate(CompanyProfileViewDirections.navigateToProduct())
            }
        })
    }
}