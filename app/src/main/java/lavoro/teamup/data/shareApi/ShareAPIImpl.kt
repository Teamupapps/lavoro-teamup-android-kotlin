package lavoro.teamup.data.shareApi

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import lavoro.teamup.core.wrapper.Result

class ShareAPIImpl(
    val context: Context
) : ShareAPI {

    override fun copyText(text: String): Result<Exception, Unit> =
        Result.build {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)
        }

    override fun shareText(text: String): Result<Exception, Unit> = Result.build {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "TeamupApp")
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(intent, "Share Text."))
    }
    override fun dialNumber(phoneNum: String): Result<Exception, Unit> = Result.build {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNum"))
        context.startActivity(callIntent)
    }
}