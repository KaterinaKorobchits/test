package my.luckydog.presentation.fragments.signin

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
import my.luckydog.common.extensions.clicks
import my.luckydog.common.extensions.throttleFirst
import my.luckydog.interactors.signin.SideEffect.Dialog
import my.luckydog.interactors.signin.SideEffect.Dialog.*
import my.luckydog.interactors.signin.SideEffect.Navigate
import my.luckydog.interactors.signin.SideEffect.Navigate.ToHome
import my.luckydog.interactors.signin.SideEffect.Navigate.ToSignUp
import my.luckydog.interactors.signin.SignInInteractor
import my.luckydog.presentation.constants.EXTRA_EMAIL
import my.luckydog.presentation.core.extensions.getViewModel
import my.luckydog.presentation.core.keyboard.KeyboardDetector
import my.luckydog.presentation.databinding.FragmentSignInBinding
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
    lateinit var dialogs: Lazy<SignInDialogs>

    @Inject
    lateinit var keyboard: KeyboardDetector

    private val viewModel: SignInViewModel by lazy(NONE) {
        getViewModel { SignInViewModel(interactor.get(), dialogs.get()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignInBinding.inflate(inflater)
        .apply {
            form = viewModel.form
            lifecycleOwner = viewLifecycleOwner

            bSignIn.clicks()
                .throttleFirst(500)
                .onEach { viewModel.onClickSignIn() }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            bSignUp.clicks()
                .throttleFirst(500)
                .onEach { viewModel.onClickSignUp() }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_EMAIL).let { viewModel.form.email.set(it) }

        viewModel.dialogs.prepare(requireContext())

        lifecycle.addObserver(viewModel)

        keyboard.observe(requireActivity().window)
            .onEach { viewModel.onKeyboardChanged(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        /*with(et_password) {
            editorActions()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .filter { it == EditorInfo.IME_ACTION_DONE }
                .subscribe { viewModel.onClickSignIn() }
        }
        with(b_sign_in) {
            clicks()
                .throttleFirst(500)
                .subscribe { viewModel.onClickSignIn() }
        }
        with(b_sign_up) {
            clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe { viewModel.onClickSignUp() }
        }*/
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
        is ToSignUp -> navigation.get().showSignUp(navigate.email)
    }

    private fun showDialog(dialog: Dialog) = when (dialog) {
        is ShowCredentialsIncorrect -> {
            viewModel.dialogs.showCredentialsIncorrect(requireContext(), dialog.error)
        }
        is ShowUnknownError -> viewModel.dialogs.showUnknownError(requireContext())
        is ShowProgress -> viewModel.dialogs.showProgress(requireContext())
        is HideProgress -> viewModel.dialogs.hideProgress()
    }
}