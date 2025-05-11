package lavoro.teamup.data.connectivity

import okhttp3.Interceptor

interface ConnectivityInterceptor : Interceptor {
    fun isOnline(): Boolean
}
