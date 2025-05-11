package lavoro.teamup.preferencesettings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import lavoro.teamup.R

class TransactionListPreference : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.tranasctionr_list_preference)
    }
}