package lavoro.teamup.data.reminder

import lavoro.teamup.core.wrapper.Result


interface ReminderAPI {
    fun setupReminderAlarmForBackup(): Result<Exception, Unit>
    fun cancelBackupReminder(): Result<Exception, Unit>
}
