package lavoro.teamup.core

import java.io.IOException

const val UNAUTHORIZED = "unauthorized."
const val DEACTIVATED = "Deactivated."
const val NO_INTERNET_CONNECTION = "No Internet Connection."

class UnAuthorizedException : Exception(UNAUTHORIZED)
class DeactivatedException : Exception(DEACTIVATED)
class NoConnectivityException : IOException(NO_INTERNET_CONNECTION)
