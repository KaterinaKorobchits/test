package my.luckydog.di.signup

import dagger.Component
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.presentation.fragments.signup.SignUpFragment

@SignUpScope
@Component(
    modules = [SignUpModule::class], dependencies = [MainActivityComponent::class]
)
interface SignUpComponent {

    fun inject(activity: SignUpFragment)
}