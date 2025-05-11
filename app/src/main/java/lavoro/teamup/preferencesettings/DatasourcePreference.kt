package lavoro.teamup.preferencesettings

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import lavoro.teamup.R
import lavoro.teamup.core.USE_REMOTE_SERVER
import lavoro.teamup.home.HomeActivityInteract
import lavoro.teamup.preferencesettings.buildlogic.PreferenceEvent
import lavoro.teamup.preferencesettings.buildlogic.PreferenceViewInjector
import lavoro.teamup.preferencesettings.buildlogic.PreferenceViewModel

class DatasourcePreference : PreferenceFragmentCompat() {
    private lateinit var viewInteract: HomeActivityInteract
    private lateinit var viewModel: PreferenceViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.datasource_preference)
        setupInteract()
        setupViews()
        setupViewModel()
        viewModel.viewModelStateObserver()
    }

    private fun setupInteract() {
        viewInteract = activity as HomeActivityInteract
    }

    private fun setupViews() {
        val swRemoteServer: SwitchPreference? = findPreference(USE_REMOTE_SERVER)

        swRemoteServer?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                activity?.let {
                    swRemoteServer?.isChecked = newValue == true
                    viewModel.handleEvent(
                        PreferenceEvent.OnServerSwitchUpdate(
                            enable = newValue == true
                        )
                    )
                }
                false
            }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@DatasourcePreference,
            factory = PreferenceViewInjector(requireActivity().application).provideViewModelFactory()
        )[PreferenceViewModel::class.java]
    }

    private fun PreferenceViewModel.viewModelStateObserver() {
        error.observe(this@DatasourcePreference) {
            viewInteract.displayToast(it.asString(requireContext()))
        }
    }
}