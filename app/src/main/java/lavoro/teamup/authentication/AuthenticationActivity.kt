package lavoro.teamup.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import lavoro.teamup.R
import lavoro.teamup.core.view.makeToast
import lavoro.teamup.core.view.setActionBarFontTitle
import lavoro.teamup.databinding.ActivityAuthenticationBinding
import lavoro.teamup.home.HomeActivity

class AuthenticationActivity :
    AppCompatActivity(),
    AuthenticationActivityInteract {

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBindingUtil()
        setupActionBarSupport()
    }

    private fun setupDataBindingUtil() {
        binding =
            DataBindingUtil.setContentView(
                this@AuthenticationActivity,
                R.layout.activity_authentication
            )
    }

    private fun setupActionBarSupport() {
        binding.toolBar.apply {
            setSupportActionBar(this)
            setActionBarFontTitle(this)
        }
    }

    override fun startDataActivity() = startActivity(
        Intent(this@AuthenticationActivity, HomeActivity::class.java)
    ).also { this@AuthenticationActivity.finish() }

    override fun displayToast(msg: String) = makeToast(msg)
}