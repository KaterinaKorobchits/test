package my.luckydog.navigation

import androidx.navigation.NavController
import my.luckydog.R
import my.luckydog.presentation.fragments.home.navigation.HomeNavigation

class HomeNavigationImpl(private val producer: NavigatorProducer) : HomeNavigation {

    override fun showSignIn() {
        producer.provide()?.navigate(R.id.home_to_signIn)
    }
}