package my.luckydog.presentation.fragments.splash

import androidx.fragment.app.Fragment
import my.luckydog.interactors.SplashInteractor
import my.luckydog.presentation.fragments.splash.navigation.SplashNavigation
import javax.inject.Inject

class SplashFragment : Fragment() {

    @Inject
    lateinit var navigation: SplashNavigation

    @Inject
    lateinit var interactor: SplashInteractor

    override fun onResume() {
        super.onResume()

        when {
            interactor.hasSession() -> navigation.showHome()
            interactor.hasAuthorize() -> navigation.showSignIn()
            else -> navigation.showSignUp()
        }
    }
}