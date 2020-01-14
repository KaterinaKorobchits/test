package my.luckydog.presentation.fragments.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dagger.Lazy
import my.luckydog.interactors.signin.SideEffect.Dialog
import my.luckydog.interactors.signin.SideEffect.Dialog.*
import my.luckydog.interactors.signin.SideEffect.Navigate
import my.luckydog.interactors.signin.SideEffect.Navigate.ToHome
import my.luckydog.interactors.signin.SideEffect.Navigate.ToSignUp
import my.luckydog.interactors.signin.SignInInteractor
import my.luckydog.presentation.constants.EXTRA_EMAIL
import my.luckydog.presentation.databinding.FragmentSignInBinding
import my.luckydog.presentation.extensions.getViewModel
import my.luckydog.presentation.fragments.signin.dialogs.SignInDialogs
import my.luckydog.presentation.fragments.signin.navigation.SignInNavigation
import my.luckydog.presentation.fragments.signin.viewmodel.SignInViewModel
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class SignInFragment : Fragment() {

    @Inject
    lateinit var navigation: Lazy<SignInNavigation>

    @Inject
    lateinit var interactor: Lazy<SignInInteractor>

    @Inject
    lateinit var dialogs: SignInDialogs

    private val viewModel: SignInViewModel by lazy(NONE) {
        getViewModel { SignInViewModel(interactor.get()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignInBinding.inflate(inflater)
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

    override fun onSaveInstanceState(outState: Bundle) {
        dialogs.hide()
        super.onSaveInstanceState(outState)
    }

    private fun navigate(navigate: Navigate) = when (navigate) {
        is ToHome -> navigation.get().showHome()
        is ToSignUp -> navigation.get().showSignUp(navigate.email)
    }

    private fun showDialog(dialog: Dialog) = when (dialog) {
        is ShowCredentialsIncorrect -> {
            dialogs.showCredentialsIncorrect(requireContext(), dialog.error)
        }
        is ShowUnknownError -> dialogs.showUnknownError(requireContext())
        is ShowProgress -> dialogs.showProgress(requireContext(), "loading...")
        is HideProgress -> dialogs.hideProgress()
        is Clear -> dialogs.clear()
    }
}