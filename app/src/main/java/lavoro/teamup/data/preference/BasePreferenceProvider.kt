package lavoro.teamup.data.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

open class BasePreferenceProvider(context: Context) {
    private val appContext = context.applicationContext

    protected val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    protected val preferenceEditor: SharedPreferences.Editor = preferences.edit()

}