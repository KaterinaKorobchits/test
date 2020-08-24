package my.luckydog.navigation

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import my.luckydog.R
import my.luckydog.presentation.constants.EXTRA_EMAIL
import my.luckydog.presentation.fragments.signup.navigation.SignUpNavigation
import my.luckydog.presentation.fragments.signup.views.AppSettingsView
import my.luckydog.presentation.fragments.signup.views.BrowserView

class SignUpNavigationImpl(
    private val context: Context,
    private val producer: NavigatorProducer,
    private val browserView: BrowserView,
    private val appSettingsView: AppSettingsView
) : SignUpNavigation {

    init {
        println("!!!init: SignUpNavigationImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    private companion object {
        private const val URL_TERMS_OF_USE = "http://www.google.com"
    }

    override fun showHome() {
        producer.provide()?.navigate(R.id.signUp_to_home)
    }

    override fun showSignIn(email: String) {
        val bundle = Bundle().apply { putString(EXTRA_EMAIL, email) }
        producer.provide()?.navigate(R.id.signUp_to_signIn, bundle)
    }

    override fun showTermsOfUse() {
        if (!browserView.openUrl(context, URL_TERMS_OF_USE))
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
    }

    override fun showAppSettings() = appSettingsView.open(context)
}