package my.luckydog.di.signup

import dagger.Component
import my.luckydog.di.auth.AuthComponent
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.presentation.fragments.signup.SignUpFragment

@FragmentScope
@Component(modules = [SignUpModule::class], dependencies = [AuthComponent::class])
interface SignUpComponent {

    fun inject(activity: SignUpFragment)
}