package lavoro.teamup.data.backups

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import lavoro.teamup.core.ACTION_DATA_BACKUPS

class BackupBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, BackupService::class.java)
        serviceIntent.action = ACTION_DATA_BACKUPS
        context?.startService(serviceIntent)
    }
}