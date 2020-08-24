package my.luckydog.navigation

import my.luckydog.R
import my.luckydog.presentation.fragments.home.navigation.HomeNavigation
import javax.inject.Inject

class HomeNavigationImpl @Inject constructor(private val producer: NavigatorProducer) :
    HomeNavigation {

    init {
        println("!!!init: HomeNavigationImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun showSignIn() {
        producer.provide()?.navigate(R.id.home_to_signIn)
    }
}