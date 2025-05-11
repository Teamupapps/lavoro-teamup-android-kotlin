package lavoro.teamup.core.wrapper

import android.util.Log

sealed class Result<out E, out V> {

    data class Value<out V>(val value: V) : Result<Nothing, V>()
    data class Error<out E>(val error: E) : Result<E, Nothing>()

    companion object Factory {
        inline fun <V> build(function: () -> V): Result<Exception, V> =
            try {
                Value(function.invoke())
            } catch (e: java.lang.Exception) {
                Log.d("TAG_RESULT", "build: ${e.message}")
                Error(e)
            }
    }
}