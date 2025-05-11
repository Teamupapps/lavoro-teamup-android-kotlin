package lavoro.teamup.data.reminder

import android.content.Context
import android.content.Intent
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.backups.BackupBroadcastReceiver

private const val RC = 0

class ReminderAPIImpl(
    private val context: Context
) : ReminderAPI {

    override fun setupReminderAlarmForBackup(): Result<Exception, Unit> =
        setReminder()

    override fun cancelBackupReminder(): Result<Exception, Unit> =
        cancelReminder()

    private fun setReminder(): Result<Exception, Unit> = Result.build {
        val intent = Intent(context, BackupBroadcastReceiver::class.java)


    }

    private fun cancelReminder(): Result<Exception, Unit> = Result.build {
        val intent = Intent(context, BackupBroadcastReceiver::class.java)

    }
}

