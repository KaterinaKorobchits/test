package my.luckydog.di.notes

import dagger.Component
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.di.rest.NetworkModule
import my.luckydog.di.rest.TranslateModule
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.presentation.fragments.notes.NotesFragment

@FragmentScope
@Component(
    modules = [NotesModule::class, NetworkModule::class, TranslateModule::class],
    dependencies = [MainActivityComponent::class]
)
interface NotesComponent {

    fun inject(fragment: NotesFragment)
}