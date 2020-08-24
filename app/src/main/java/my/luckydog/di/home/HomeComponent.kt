package my.luckydog.di.home

import dagger.Component
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.di.rest.ImageModule
import my.luckydog.di.rest.NetworkModule
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.presentation.fragments.home.HomeFragment

@FragmentScope
@Component(
    modules = [HomeModule::class, NetworkModule::class, ImageModule::class], dependencies = [MainActivityComponent::class]
)
interface HomeComponent {

    fun inject(activity: HomeFragment)
}