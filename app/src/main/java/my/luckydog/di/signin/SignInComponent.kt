package my.luckydog.di.signin

import dagger.Component
import my.luckydog.di.auth.AuthComponent
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.presentation.fragments.signin.SignInFragment

@FragmentScope
@Component(modules = [SignInModule::class], dependencies = [AuthComponent::class])
interface SignInComponent {

    fun inject(activity: SignInFragment)
}