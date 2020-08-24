package my.luckydog.presentation.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.Lazy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import my.luckydog.interactors.signup.SideEffect.Dialog
import my.luckydog.interactors.signup.SideEffect.Dialog.*
import my.luckydog.interactors.signup.SideEffect.Navigate
import my.luckydog.interactors.signup.SideEffect.Navigate.*
import my.luckydog.interactors.signup.SignUpInteractor
import my.luckydog.presentation.constants.EXTRA_EMAIL
import my.luckydog.presentation.core.extensions.getViewModel
import my.luckydog.presentation.core.keyboard.KeyboardDetector
import my.luckydog.presentation.databinding.FragmentSignUpBinding
import my.luckydog.presentation.fragments.signup.dialogs.SignUpDialogs
import my.luckydog.presentation.fragments.signup.navigation.SignUpNavigation
import my.luckydog.presentation.fragments.signup.viewmodel.SignUpViewModel
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class SignUpFragment : Fragment() {

    @Inject
    lateinit var navigation: Lazy<SignUpNavigation>

    @Inject
    lateinit var interactor: Lazy<SignUpInteractor>

    @Inject
    lateinit var dialogs: Lazy<SignUpDialogs>

    @Inject
    lateinit var keyboard: KeyboardDetector

    private val viewModel: SignUpViewModel by lazy(NONE) {
        getViewModel { SignUpViewModel(interactor.get(), dialogs.get()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignUpBinding.inflate(inflater)
        .apply {
            handler = viewModel
            form = viewModel.form
            lifecycleOwner = viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_EMAIL).let { viewModel.form.email.set(it) }

        viewModel.dialogs.prepare(requireContext())

        lifecycle.addObserver(viewModel)

        keyboard.observe(requireActivity().window)
            .onEach { viewModel.onKeyboardChanged(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.navigate.observe(viewLifecycleOwner, Observer { event ->
            event.getContent()?.let { navigate(it) }
        })

        viewModel.showDialog.observe(viewLifecycleOwner, Observer { event ->
            event.getContent()?.let { showDialog(it) }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.dialogs.dismiss()
    }

    private fun navigate(navigate: Navigate) = when (navigate) {
        is ToHome -> navigation.get().showHome()
        is ToSignIn -> navigation.get().showSignIn(navigate.email)
        is ToTerms -> navigation.get().showTermsOfUse()
        is ToAppSettings -> navigation.get().showAppSettings()
    }

    private fun showDialog(dialog: Dialog) = when (dialog) {
        is ShowUnknownError -> viewModel.dialogs.showUnknownError(requireContext())
        is ShowProgress -> viewModel.dialogs.showProgress(requireContext())
        is HideProgress -> viewModel.dialogs.hideProgress()
    }
}