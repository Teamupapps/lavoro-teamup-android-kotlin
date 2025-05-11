package lavoro.teamup.notedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import lavoro.teamup.R
import lavoro.teamup.core.binding.ViewBindingHolder
import lavoro.teamup.core.binding.ViewBindingHolderImpl
import lavoro.teamup.core.view.MAX_NOTE_DIG
import lavoro.teamup.core.view.MIN_TITLE_DIG
import lavoro.teamup.core.view.beginTitleLengthLayoutWatcher
import lavoro.teamup.core.view.checkValidation
import lavoro.teamup.core.view.toEditable
import lavoro.teamup.core.wrapper.EventObserver
import lavoro.teamup.databinding.ViewNoteDetailsBinding
import lavoro.teamup.home.HomeActivityInteract
import lavoro.teamup.notedetails.buildlogic.NoteViewInjector

class NoteDetailsView : Fragment(), View.OnClickListener,
    ViewBindingHolder<ViewNoteDetailsBinding> by ViewBindingHolderImpl() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var viewInteract: HomeActivityInteract

    override fun onDestroyView() {
        super.onDestroyView()
        destroyBinding()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_update) viewModel.handleEvent(
            NoteDetailsViewEvent.OnUpdateTxtClick(
                title = binding?.itemTitle?.edText?.text?.trim().toString()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = initBinding(ViewNoteDetailsBinding.inflate(layoutInflater), this@NoteDetailsView) {
        setupViewInteract()
        setupViewInputs()
        setupViewModel()
        viewModel.startObserving()
        setupClickListener()
    }

    private fun setupViewInteract() {
        viewInteract = activity as HomeActivityInteract
    }

    private fun setupViewInputs() {
        binding?.apply {
            itemTitle.apply {

                textInputLayout.hint = getString(R.string.note)

                edText.beginTitleLengthLayoutWatcher(
                    inputLayout = itemTitle.textInputLayout,
                    errorMsg = getString(R.string.invalid),
                    minDig = MIN_TITLE_DIG,
                    maxDig = MAX_NOTE_DIG,
                )
            }

            btnUpdate.setOnClickListener(this@NoteDetailsView)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            owner = this@NoteDetailsView,
            factory = NoteViewInjector(requireActivity().application).provideViewModelFactory()
        )[NoteViewModel::class.java]
        viewModel.handleEvent(NoteDetailsViewEvent.OnStartGetNote)
    }

    private fun setupClickListener() {
        binding?.switchAdmin?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView?.isPressed == true) viewModel.handleEvent(
                NoteDetailsViewEvent.OnAdminSwitchCheck(
                    admin = isChecked
                )
            )
        }
    }

    private fun NoteViewModel.startObserving() {
        loading.observe(viewLifecycleOwner) {
            viewInteract.updateProgressLoad(it)
        }
        error.observe(viewLifecycleOwner) {
            viewInteract.displayToast(it.asString(context))
        }
        adminSwitchState.observe(viewLifecycleOwner) {
            binding?.switchAdmin?.isChecked = it
        }
        noteValidateState.observe(viewLifecycleOwner) {
            binding?.itemTitle?.textInputLayout?.checkValidation(
                validate = it, errorMsg = context?.getString(R.string.invalid)
            )
        }
        updated.observe(viewLifecycleOwner, EventObserver {
            if (findNavController().currentDestination?.id == R.id.noteDetailsView) findNavController().navigate(
                NoteDetailsViewDirections.navigateToTransactionList()
            )
        })
        note.observe(viewLifecycleOwner) {
            if (it.title.isNotBlank()) binding?.itemTitle?.edText?.text = it.title.toEditable()
        }
    }
}