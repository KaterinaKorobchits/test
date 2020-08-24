package my.luckydog.di.settings

import dagger.Component
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.presentation.fragments.settings.SettingsFragment

@FragmentScope
@Component(dependencies = [MainActivityComponent::class])
interface SettingsComponent {

    fun inject(fragment: SettingsFragment)
}