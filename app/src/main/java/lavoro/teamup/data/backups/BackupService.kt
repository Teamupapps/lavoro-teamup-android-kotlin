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
import lavoro.teamup.core.getCalendarSearchDay
import lavoro.teamup.core.lazyDeferred
import lavoro.teamup.core.mapping.toTransaction
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.backups.notifications.NotificationManager
import lavoro.teamup.data.connectivity.ConnectivityInterceptorImpl
import lavoro.teamup.data.exportApi.ExcelAPIImpl
import lavoro.teamup.data.implementation.TransactionRepositoryImpl
import lavoro.teamup.data.model.entry.HistoryEntry
import lavoro.teamup.data.model.transaction.RemoteTransaction
import lavoro.teamup.data.preference.datalist.TransactionListPreferenceImpl
import lavoro.teamup.data.preference.transaction.TransactionFilterPreferenceImpl
import lavoro.teamup.data.preference.util.UtilPreferenceImpl
import lavoro.teamup.data.room.TeamDatabase

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

                val deferredTransactionList by lazyDeferred {
                    when (val result = TransactionRepositoryImpl(
                        listPreference = TransactionListPreferenceImpl(application),
                        filterPreference = TransactionFilterPreferenceImpl(application),
                        local = TeamDatabase.invoke(application).transactionDao(),
                        localUser = TeamDatabase.invoke(application).userDao(),
                        utilPreference = UtilPreferenceImpl(application),
                        connectInterceptor = ConnectivityInterceptorImpl(application),
                    ).filterTransactionList(
                        item = RemoteTransaction(
                            historyEntry = HistoryEntry(
                                creationDate = getCalendarSearchDay(day = -1)
                            )
                        ).toTransaction
                    )) {
                        is Result.Error -> {
                            println("Backups: Transaction exporting result error ${result.error}")
                            emptyList()
                        }

                        is Result.Value -> {
                            println("Backups: Transaction exporting..size=${result.value.size}")
                            result.value
                        }
                    }
                }

                deferredTransactionList.await().let {

                    when (val result = ExcelAPIImpl().buildTransactionListFile(
                        list = deferredTransactionList.await()
                    )) {
                        is Result.Error -> {
                            println("Backups: Transaction exporting Result error ${result.error}")
                            stopService()
                        }

                        is Result.Value -> {
                            println("Backups: Transaction exporting completed.")
                            stopService()
                        }
                    }

                }
            }

            STOP_SERVICE -> stopService()
        }




        return START_STICKY
    }

}