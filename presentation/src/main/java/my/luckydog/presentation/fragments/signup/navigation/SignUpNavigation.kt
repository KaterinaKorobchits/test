package my.luckydog.presentation.fragments.signup.navigation

interface SignUpNavigation {

    fun showHome()

    fun showSignIn(email: String)

    fun showTermsOfUse()

    fun showAppSettings()
}