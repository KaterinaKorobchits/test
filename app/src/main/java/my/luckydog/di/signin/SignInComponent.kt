package my.luckydog.di.signin

import dagger.Component
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.presentation.fragments.signin.SignInFragment

@SignInScope
@Component(modules = [SignInModule::class], dependencies = [MainActivityComponent::class])
interface SignInComponent {

    fun inject(activity: SignInFragment)
}