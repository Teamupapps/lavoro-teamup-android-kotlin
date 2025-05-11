package lavoro.teamup.authentication.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import lavoro.teamup.R
import lavoro.teamup.authentication.AuthenticationActivityInteract
import lavoro.teamup.authentication.AuthenticationViewEvent
import lavoro.teamup.authentication.BaseAuthenticationView
import lavoro.teamup.authentication.signup.buildlogic.SignUpViewInjector
import lavoro.teamup.core.binding.ViewBindingHolder
import lavoro.teamup.core.binding.ViewBindingHolderImpl
import lavoro.teamup.core.view.checkValidation
import lavoro.teamup.core.view.visible
import lavoro.teamup.databinding.ViewAuthenticationBinding

class SignupView : BaseAuthenticationView(),
    View.OnClickListener,
    ViewBindingHolder<ViewAuthenticationBinding> by ViewBindingHolderImpl() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var viewInteract: AuthenticationActivityInteract

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_auth) viewModel.handleEvent(
            AuthenticationViewEvent.OnSignupBtnClick(
                email = binding?.itemEmail?.edText?.text?.trim().toString(),
                pass = binding?.itemPassword?.edText?.text?.trim().toString()
            )
        )
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
            this@SignupView
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
            txtOr.visible(isVisible = false)
            btnSignup.visible(isVisible = false)
            btnGoogleLogin.visible(isVisible = false)
            progressBar.visible(isVisible = false)

            btnAuth.text = getString(R.string.create_account)
            btnAuth.setOnClickListener(this@SignupView)

            itemEmail.edText.setUserEmailInput(
                inputLayout = itemEmail.textInputLayout
            )
            itemPassword.edText.setUserPasswordInput(
                inputLayout = itemPassword.textInputLayout
            )
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@SignupView,
            factory = SignUpViewInjector(requireActivity().application).provideViewModelFactory()
        )[SignUpViewModel::class.java]
    }

    private fun SignUpViewModel.startObserving() {
        loading.observe(viewLifecycleOwner) {
            binding?.progressBar?.visible(it)
        }
        error.observe(viewLifecycleOwner) {
            viewInteract.displayToast(it.asString(context) ?: getString(R.string.connecting))
        }
        updated.observe(viewLifecycleOwner) {
            findNavController().navigate(SignupViewDirections.navigateToLogin())
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
    }

}

