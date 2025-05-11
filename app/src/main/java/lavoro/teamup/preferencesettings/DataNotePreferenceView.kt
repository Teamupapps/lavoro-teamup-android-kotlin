package lavoro.teamup.preferencesettings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import lavoro.teamup.R

class DataNotePreferenceView : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.data_note_preferences)
    }
}