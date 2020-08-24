package my.luckydog.di.splash

import dagger.Component
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.presentation.fragments.splash.SplashFragment

@FragmentScope
@Component(
    modules = [SplashModule::class], dependencies = [MainActivityComponent::class]
)
interface SplashComponent {

    fun inject(activity: SplashFragment)
}