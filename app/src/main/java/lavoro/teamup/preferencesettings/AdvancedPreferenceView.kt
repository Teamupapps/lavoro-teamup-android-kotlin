package lavoro.teamup.preferencesettings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import lavoro.teamup.R

class AdvancedPreferenceView : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.advanced_preferences)
    }
}
