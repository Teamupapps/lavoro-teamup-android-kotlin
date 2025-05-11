package lavoro.teamup.data.backups

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lavoro.teamup.core.ACTION_DATA_BACKUPS
import lavoro.teamup.core.STOP_SERVICE
import lavoro.teamup.data.backups.notifications.NotificationManager

class BackupService : Service() {

    private val jobTracker = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        startForeground(2, NotificationManager.startForegroundServiceNotification(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        jobTracker.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        fun stopService() {
            stopForeground(true)
            stopSelfResult(startId)
        }

        when (intent?.action) {
            ACTION_DATA_BACKUPS -> coroutineScope.launch {

            }

            STOP_SERVICE -> stopService()
        }




        return START_STICKY
    }

}