package lavoro.teamup.preferencesettings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import lavoro.teamup.R

class TransactionFilterListPreference : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.tranasction_filter_list_preference)
    }
}