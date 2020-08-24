package my.luckydog.di.learn

import dagger.Component
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.presentation.fragments.learn.LearnFragment

@FragmentScope
@Component(
    modules = [LearnModule::class], dependencies = [MainActivityComponent::class]
)
interface LearnComponent {

    fun inject(fragment: LearnFragment)
}