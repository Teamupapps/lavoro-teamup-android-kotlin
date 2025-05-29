package lavoro.teamup.data.backups.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import lavoro.teamup.R
import lavoro.teamup.home.HomeActivity

object NotificationManager {

    private const val FORE_NOTIFICATION_CHANNEL_ID = "teamup.backups.services"
    private const val FORE_CHANNEL_NAME = "TeamupApp Services"

    fun startForegroundServiceNotification(context: Context): Notification? {
        val nChannel =
            NotificationChannel(
                FORE_NOTIFICATION_CHANNEL_ID, FORE_CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE
            )

        val manager =
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

        val nBuilder = NotificationCompat.Builder(context, FORE_NOTIFICATION_CHANNEL_ID)

        manager.createNotificationChannel(nChannel)

        return nBuilder.setOngoing(true).setContentTitle(context.getString(R.string.backups))
            .setContentText(context.getString(R.string.backups_start))
            .setContentIntent(getPendingIntentWithStack(context, HomeActivity::class.java))
            .setPriority(NotificationManager.IMPORTANCE_MIN).setSmallIcon(R.drawable.ic_arrow_buy)
            .setCategory(Notification.CATEGORY_SERVICE).build()
    }

    private fun <T> getPendingIntentWithStack(
        context: Context, javaClass: Class<T>
    ): PendingIntent {
        val resultIntent = Intent(context, javaClass)
        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(javaClass)
        stackBuilder.addNextIntent(resultIntent)

        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}
