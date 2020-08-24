package my.luckydog.navigation

import android.os.Bundle
import my.luckydog.R
import my.luckydog.presentation.constants.EXTRA_EMAIL
import my.luckydog.presentation.fragments.signin.navigation.SignInNavigation
import javax.inject.Inject

class SignInNavigationImpl @Inject constructor(private val producer: NavigatorProducer) :
    SignInNavigation {

    init {
        println("!!!init: SignInNavigationImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun showHome() {
        producer.provide()?.navigate(R.id.signIn_to_home)
    }

    override fun showSignUp(email: String) {
        val bundle = Bundle().apply { putString(EXTRA_EMAIL, email) }
        producer.provide()?.navigate(R.id.signIn_to_signUp, bundle)
    }
}