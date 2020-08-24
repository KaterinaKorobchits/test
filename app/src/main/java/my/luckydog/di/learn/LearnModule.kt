package my.luckydog.di.learn

import dagger.Binds
import dagger.Module
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.navigation.LearnNavigationImpl
import my.luckydog.presentation.fragments.learn.LearnNavigation

@Module
abstract class LearnModule {

    @Binds
    @FragmentScope
    abstract fun provideNavigation(navigation: LearnNavigationImpl): LearnNavigation
}