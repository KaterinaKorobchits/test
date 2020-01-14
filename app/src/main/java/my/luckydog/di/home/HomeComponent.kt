package my.luckydog.di.home

import dagger.Component
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.presentation.fragments.home.HomeFragment

@HomeScope
@Component(
    modules = [HomeModule::class], dependencies = [MainActivityComponent::class]
)
interface HomeComponent {

    fun inject(activity: HomeFragment)
}