package lavoro.teamup

import android.app.Application
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen

class TeamupApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this@TeamupApp)
        AndroidThreeTen.init(this@TeamupApp)
    }
}
