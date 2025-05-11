package lavoro.teamup.teamlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import lavoro.teamup.R
import lavoro.teamup.core.binding.ViewBindingHolder
import lavoro.teamup.core.binding.ViewBindingHolderImpl
import lavoro.teamup.core.view.isQueryMatch
import lavoro.teamup.core.view.relaunchCurrentView
import lavoro.teamup.core.view.setupListItemDecoration
import lavoro.teamup.core.view.visible
import lavoro.teamup.data.model.team.Team
import lavoro.teamup.databinding.ViewDataExpandedListBinding
import lavoro.teamup.home.HomeActivityInteract
import lavoro.teamup.teamlist.adapter.TeamAdapter
import lavoro.teamup.teamlist.buildlogic.TeamViewInjector

class TeamView : Fragment(),
    ViewBindingHolder<ViewDataExpandedListBinding> by ViewBindingHolderImpl() {

    private lateinit var viewModel: TeamViewModel
    private lateinit var viewInteract: HomeActivityInteract
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var teamAdapter: TeamAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        destroyBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(ViewDataExpandedListBinding.inflate(layoutInflater), this@TeamView) {
        setupViewInteract()
        setupBottomSheet()
        setupViewInputs()
        setupListAdapter()
        setupViewModel()
        viewModel.setupStatesObserver()
    }

    private fun setupBottomSheet() {
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

            itemSearch.mtSearchView.queryHint = getString(R.string.team_search)

        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@TeamView,
            factory = TeamViewInjector(requireActivity().application).provideTeamListViewModelFactory()
        )[TeamViewModel::class.java]
        viewModel.handleEvent(TeamViewEvent.GetTeam)
    }


    private fun setupListAdapter() {
        teamAdapter = TeamAdapter()
        binding?.recDataList?.apply {
            setupListItemDecoration(context)
            adapter = teamAdapter
        }
        teamAdapter.event.observe(viewLifecycleOwner) {
            viewModel.handleEvent(it)
        }
    }

    private fun setupSearchViewListener(list: List<Team>) {
        binding?.itemSearch?.mtSearchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val teamList = mutableListOf<Team>()

                    for (team: Team in list)
                        if (team.name.isQueryMatch(it))
                            teamList.add(team)
                        else if (team.email.isQueryMatch(it))
                            teamList.add(team)

                    if (teamList.isNotEmpty()) teamAdapter.submitList(teamList)
                }
                return true
            }
        })
    }

    private fun TeamViewModel.setupStatesObserver() {
        loading.observe(viewLifecycleOwner) {
            viewInteract.updateProgressLoad(it)
        }
        error.observe(viewLifecycleOwner) {
            viewInteract.displayToast(it.asString(context))
        }
        updated.observe(viewLifecycleOwner) {
            relaunchCurrentView()
        }
        teamList.observe(viewLifecycleOwner) {
            teamAdapter.submitList(it)
            setupSearchViewListener(it)
        }
    }
}