package lavoro.teamup.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lavoro.teamup.R
import lavoro.teamup.authentication.AuthenticationActivity
import lavoro.teamup.core.setActionBarFontTitle
import lavoro.teamup.core.view.makeToast
import lavoro.teamup.core.view.visible
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.shareApi.ShareAPIImpl
import lavoro.teamup.databinding.ActivityHomeBinding
import lavoro.teamup.home.buildlogic.HomeActivityInjector

private const val DELAY_LOGIN = 1000L

class HomeActivity : HomeActivityUgly(), HomeActivityInteract {

    private lateinit var homeBinding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBindingUtil()
        setupActionBarSupport()
        setupNavigationController()
        setupViewModel()
        viewModel.startObserving()
    }

    private fun setupDataBindingUtil() {
        homeBinding = DataBindingUtil.setContentView(this@HomeActivity, R.layout.activity_home)
    }

    private fun setupActionBarSupport() {
        homeBinding.contentHome.toolBar.apply {
            setSupportActionBar(this)
            setActionBarFontTitle(toolbar = this)
        }
    }

    private fun setupNavigationController() = try {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
        homeBinding.contentHome.bottomNavigationView.setupWithNavController(navController)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this, HomeActivityInjector(this.application).provideViewModelFactory()
        )[HomeActivityViewModel::class.java]
        viewModel.handleEvent(HomeActivityEvent.OnStartGetUser)
    }

    private fun HomeActivityViewModel.startObserving() {
        loading.observe(this@HomeActivity) {
            updateProgressLoad(loading = it)
        }
        error.observe(this@HomeActivity) {

            displayToast(it.asString(this@HomeActivity))
        }
        updateActionToolbarAttempt.observe(this@HomeActivity) {
            updateActionBarSubTitle(
                (it.asString(this@HomeActivity) ?: getString(R.string.connecting))
            )
        }
        updateActionToolbarColorAttempt.observe(this@HomeActivity) {
            updateActionBarSubTitleColor(it)
        }
        loginAttempt.observe(this@HomeActivity) {
            startAuthActivity()
        }
        requestPermissionsAttempt.observe(this@HomeActivity) {
            checkWriteStoragePermission()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cache -> {
                navController.navigateUp()
                navController.navigate(R.id.cachePreferenceView)
                true
            }

            R.id.menu_backups -> {
                navController.navigateUp()
                navController.navigate(R.id.dataBackupsPreference)
                true
            }

            R.id.menu_advanced -> {
                navController.navigateUp()
                navController.navigate(R.id.advancedPreferenceView)
                true
            }

            R.id.menu_datasource -> {
                navController.navigateUp()
                navController.navigate(R.id.datasourcePreference)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun startAuthActivity() {
        lifecycleScope.launch {
            delay(DELAY_LOGIN)
            startActivity(
                Intent(this@HomeActivity, AuthenticationActivity::class.java)
            ).also { this@HomeActivity.finish() }
        }
    }

    override fun restartHomeActivity() {
        lifecycleScope.launch {
            delay(DELAY_LOGIN)
            val intent: Intent? = applicationContext.packageManager
                .getLaunchIntentForPackage(applicationContext.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun finishHomeActivity() = finish()

    override fun displayToast(msg: String?) = makeToast(msg ?: getString(R.string.connecting))

    override fun updateProgressLoad(loading: Boolean) {
        homeBinding.contentHome.progressCircular.visible(loading)
    }

    override fun updateActionBaTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun updateActionBarSubTitle(title: String) {
        supportActionBar?.subtitle = title
    }

    override fun updateActionBarSubTitleColor(isError: Boolean) {
        if (isError) homeBinding.contentHome.toolBar.setSubtitleTextColor(
            ContextCompat.getColor(
                this@HomeActivity, R.color.violet
            )
        ) else Unit
    }

    override fun actionCopyText(text: String) {
        val result = ShareAPIImpl(this@HomeActivity).copyText(text)
        if (result is Result.Value) displayToast(getString(R.string.copy_success))
    }

    override fun actionShareText(text: String) {
        ShareAPIImpl(this@HomeActivity).shareText(text)
    }

    override fun actionDial(phoneNum: String) {
        ShareAPIImpl(this@HomeActivity).dialNumber(phoneNum)
    }

    override fun displaySnack(text: String, actionText: String?, action: (() -> Unit)?) {
        val snackBar: Snackbar = Snackbar
            .make(homeBinding.contentHome.fragmentContainer, text, Snackbar.LENGTH_LONG)
            .setAction(actionText ?: getString(R.string.connecting)) { action?.invoke() }

        snackBar.setAnchorView(homeBinding.contentHome.bottomNavigationView)

        snackBar.show()
    }
}