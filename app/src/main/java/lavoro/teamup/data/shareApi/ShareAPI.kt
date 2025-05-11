package lavoro.teamup.data.shareApi

import lavoro.teamup.core.wrapper.Result

interface ShareAPI {
    fun copyText(text: String): Result<Exception, Unit>
    fun shareText(text: String): Result<Exception, Unit>
    fun dialNumber(phoneNum: String): Result<Exception, Unit>

}