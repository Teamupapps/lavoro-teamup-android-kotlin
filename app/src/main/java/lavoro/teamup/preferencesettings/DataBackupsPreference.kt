package lavoro.teamup.preferencesettings

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import lavoro.teamup.R
import lavoro.teamup.core.USE_BACKUPS
import lavoro.teamup.home.HomeActivityInteract
import lavoro.teamup.preferencesettings.buildlogic.PreferenceEvent
import lavoro.teamup.preferencesettings.buildlogic.PreferenceViewInjector
import lavoro.teamup.preferencesettings.buildlogic.PreferenceViewModel

class DataBackupsPreference : PreferenceFragmentCompat() {
    private lateinit var viewInteract: HomeActivityInteract
    private lateinit var viewModel: PreferenceViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.backups_preference)
        setupInteract()
        setupViewModel()
        setupViews()
        viewModel.viewModelStateObserver()
    }

    private fun setupInteract() {
        viewInteract = activity as HomeActivityInteract
    }

    private fun setupViews() {
        val swBackups: SwitchPreference? = findPreference(USE_BACKUPS)

        swBackups?.apply {
            onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    isChecked = newValue == true
                    viewModel.handleEvent(
                        PreferenceEvent.OnBackupTransactionSwitchUpdate(
                            enable = newValue == true
                        )
                    )
                    false
                }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@DataBackupsPreference,
            factory = PreferenceViewInjector(requireActivity().application).provideViewModelFactory()
        )[PreferenceViewModel::class.java]
    }

    private fun PreferenceViewModel.viewModelStateObserver() {
        error.observe(this@DataBackupsPreference) {
            viewInteract.displayToast(it.asString(requireContext()))
        }
        backupText.observe(this@DataBackupsPreference) {
            viewInteract.displayToast(it.asString(context))
        }
    }
}