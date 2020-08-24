package my.luckydog.navigation

import my.luckydog.R
import my.luckydog.presentation.fragments.splash.navigation.SplashNavigation
import javax.inject.Inject

class SplashNavigationImpl @Inject constructor(private val producer: NavigatorProducer) :
    SplashNavigation {

    init {
        println("!!!init: SplashNavigationImpl - ${this::class.java.name} ${this.hashCode()}")
    }

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