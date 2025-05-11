package lavoro.teamup.preferencesettings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import lavoro.teamup.R

class DataStockPreferenceView : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.data_stock_preferences)
    }
}