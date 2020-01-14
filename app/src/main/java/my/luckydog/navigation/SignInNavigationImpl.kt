package my.luckydog.navigation

import android.os.Bundle
import androidx.navigation.NavController
import my.luckydog.R
import my.luckydog.presentation.constants.EXTRA_EMAIL
import my.luckydog.presentation.fragments.signin.navigation.SignInNavigation

class SignInNavigationImpl(private val producer: NavigatorProducer) : SignInNavigation {

    override fun showHome() {
        producer.provide()?.navigate(R.id.signIn_to_home)
    }

    override fun showSignUp(email: String) {
        val bundle = Bundle().apply { putString(EXTRA_EMAIL, email) }
        producer.provide()?.navigate(R.id.signIn_to_signUp, bundle)
    }
}