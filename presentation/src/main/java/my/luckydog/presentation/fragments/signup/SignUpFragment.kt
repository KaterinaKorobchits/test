package my.luckydog.presentation.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dagger.Lazy
import my.luckydog.interactors.signup.SideEffect.Dialog
import my.luckydog.interactors.signup.SideEffect.Dialog.*
import my.luckydog.interactors.signup.SideEffect.Navigate
import my.luckydog.interactors.signup.SideEffect.Navigate.*
import my.luckydog.interactors.signup.SignUpInteractor
import my.luckydog.presentation.constants.EXTRA_EMAIL
import my.luckydog.presentation.databinding.FragmentSignUpBinding
import my.luckydog.presentation.extensions.getViewModel
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
    lateinit var dialogs: SignUpDialogs

    private val viewModel: SignUpViewModel by lazy(NONE) {
        getViewModel { SignUpViewModel(interactor.get()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignUpBinding.inflate(inflater)
        .apply {
            handler = viewModel
            form = viewModel.form
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_EMAIL).let { viewModel.form.email.set(it) }

        lifecycle.addObserver(viewModel)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.navigate.observe(viewLifecycleOwner, Observer { navigate(it) })

        viewModel.showDialog.observe(viewLifecycleOwner, Observer { showDialog(it) })
    }

    private fun navigate(navigate: Navigate) = when (navigate) {
        is ToHome -> navigation.get().showHome()
        is ToSignIn -> navigation.get().showSignIn(navigate.email)
        is ToTerms -> navigation.get().showTermsOfUse()
    }

    private fun showDialog(dialog: Dialog) = when (dialog) {
        is ShowUnknownError -> dialogs.showUnknownError(requireContext())
        is ShowProgress -> dialogs.showProgress(requireContext(), "loading...")
        is HideProgress -> dialogs.hideProgress()
        is Clear -> dialogs.clear()
    }
}