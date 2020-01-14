package my.luckydog.navigation

import androidx.navigation.NavController
import my.luckydog.R
import my.luckydog.presentation.fragments.splash.navigation.SplashNavigation

class SplashNavigationImpl(private val producer: NavigatorProducer) : SplashNavigation {

    override fun showHome() {
        producer.provide()?.navigate(R.id.splash_to_home)
    }

    override fun showSignIn() {
        producer.provide()?.navigate(R.id.splash_to_signIn)
    }

    override fun showSignUp() {
        producer.provide()?.navigate(R.id.splash_to_signUp)
    }
}