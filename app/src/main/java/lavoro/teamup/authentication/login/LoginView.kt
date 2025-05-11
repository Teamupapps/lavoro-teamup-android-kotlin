package lavoro.teamup.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import lavoro.teamup.R
import lavoro.teamup.authentication.AuthenticationActivityInteract
import lavoro.teamup.authentication.AuthenticationViewEvent
import lavoro.teamup.authentication.BaseAuthenticationView
import lavoro.teamup.authentication.login.buildlogic.LoginViewInjector
import lavoro.teamup.core.SIGN_IN_REQUEST_CODE
import lavoro.teamup.core.binding.ViewBindingHolder
import lavoro.teamup.core.binding.ViewBindingHolderImpl
import lavoro.teamup.core.view.checkValidation
import lavoro.teamup.core.view.enable
import lavoro.teamup.core.view.visible
import lavoro.teamup.data.model.LoginResult
import lavoro.teamup.databinding.ViewAuthenticationBinding

class LoginView : BaseAuthenticationView(),
    View.OnClickListener,
    ViewBindingHolder<ViewAuthenticationBinding> by ViewBindingHolderImpl() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewInteract: AuthenticationActivityInteract

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_signup -> if (findNavController().currentDestination?.id == R.id.loginView)
                findNavController().navigate(LoginViewDirections.navigateToSignup()) else Unit

            R.id.btn_google_login -> viewModel.handleEvent(AuthenticationViewEvent.OnAuthBtnClick)

            R.id.btn_auth -> viewModel.handleEvent(
                AuthenticationViewEvent.OnLoginBtnClick(
                    email = binding?.itemEmail?.edText?.text?.trim().toString(),
                    pass = binding?.itemPassword?.edText?.text?.trim().toString()
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        initBinding(
            ViewAuthenticationBinding.inflate(layoutInflater),
            this@LoginView
        ) {
            setupViewInteract()
            setupViews()
            setupViewModel()
            viewModel.startObserving()
        }

    private fun setupViewInteract() {
        viewInteract = activity as AuthenticationActivityInteract
    }

    private fun setupViews() {
        binding?.apply {
            btnAuth.setOnClickListener(this@LoginView)
            btnSignup.setOnClickListener(this@LoginView)
            btnGoogleLogin.setOnClickListener(this@LoginView)

            itemEmail.edText.setUserEmailInput(
                inputLayout = itemEmail.textInputLayout
            )
            itemPassword.edText.setUserPasswordInput(
                inputLayout = itemPassword.textInputLayout
            )

            (btnGoogleLogin.getChildAt(0) as TextView).text =
                getString(R.string.continue_with_google)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@LoginView,
            factory = LoginViewInjector(requireActivity().application).provideViewModelFactory()
        )[LoginViewModel::class.java]
        viewModel.handleEvent(AuthenticationViewEvent.GetAuthUser)
    }

    private fun startSignInFlow() = try {
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE)

    } catch (ex: Exception) {
        viewInteract.displayToast(getString(R.string.unable_to_sign_in))
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)/*
        *
        *  +TO DO
        * Enable Google Sign in method
        *
        * Add a support email address to your project in project settings.
        * Open link https://console.firebase.google.com/
        *
        * */
        try {
            val userToken: String?
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            if (account != null) {
                userToken = account.idToken
                viewModel.handleEvent(
                    AuthenticationViewEvent.OnGoogleSignInResult(
                        LoginResult(requestCode, userToken)
                    )
                )
            }
        } catch (ex: Exception) {
            viewInteract.displayToast(
                getString(R.string.unable_to_sign_in) +
                        ex.message
            )
        }
    }

    private fun LoginViewModel.startObserving() {
        loading.observe(viewLifecycleOwner) {
            binding?.progressBar?.visible(it)
        }
        error.observe(viewLifecycleOwner) {
            viewInteract.displayToast(it.asString(context) ?: getString(R.string.connecting))
        }
        updated.observe(viewLifecycleOwner) {
            viewInteract.startDataActivity()
        }
        emailValidateState.observe(viewLifecycleOwner) {
            binding?.itemEmail?.textInputLayout?.checkValidation(
                it, getString(R.string.invalid)
            )
        }
        passwordValidateState.observe(viewLifecycleOwner) {
            binding?.itemPassword?.textInputLayout?.checkValidation(
                it, getString(R.string.invalid)
            )
        }
        updateLoginButtonAttempt.observe(viewLifecycleOwner) {
            binding?.btnAuth?.text = it.asString(context)
        }
        googleAuthAttempt.observe(viewLifecycleOwner) {
            startSignInFlow()
        }
        signed.observe(viewLifecycleOwner) {
            binding?.apply {
                btnAuth.enable(it)
                btnSignup.visible(it)
                txtOr.visible(it)
                btnGoogleLogin.visible(it)
            }
        }
    }

}