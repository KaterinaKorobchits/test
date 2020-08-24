package my.luckydog.activity

import androidx.fragment.app.Fragment
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.di.auth.AuthComponent
import my.luckydog.di.auth.DaggerAuthComponent
import my.luckydog.di.home.DaggerHomeComponent
import my.luckydog.di.learn.DaggerLearnComponent
import my.luckydog.di.notes.DaggerNotesComponent
import my.luckydog.di.settings.DaggerSettingsComponent
import my.luckydog.di.signin.DaggerSignInComponent
import my.luckydog.di.signup.DaggerSignUpComponent
import my.luckydog.di.splash.DaggerSplashComponent
import my.luckydog.presentation.fragments.home.HomeFragment
import my.luckydog.presentation.fragments.learn.LearnFragment
import my.luckydog.presentation.fragments.notes.NotesFragment
import my.luckydog.presentation.fragments.settings.SettingsFragment
import my.luckydog.presentation.fragments.signin.SignInFragment
import my.luckydog.presentation.fragments.signup.SignUpFragment
import my.luckydog.presentation.fragments.splash.SplashFragment
import javax.inject.Inject

class MainInjectorImpl @Inject constructor() : MainInjector {

    init {
        println("!!!init: MainInjectorImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    private var authComponent: AuthComponent? = null

    override fun inject(fragment: Fragment, mainComponent: MainActivityComponent) {
        when (fragment) {
            is SplashFragment -> {
                DaggerSplashComponent.builder()
                    .mainActivityComponent(mainComponent)
                    .build()
                    .inject(fragment)
            }
            is SignUpFragment -> {
                DaggerSignUpComponent.builder()
                    .authComponent(getAuthComponent(mainComponent))
                    .build()
                    .inject(fragment)
            }
            is SignInFragment -> {
                DaggerSignInComponent.builder()
                    .authComponent(getAuthComponent(mainComponent))
                    .build()
                    .inject(fragment)
            }
            is HomeFragment -> {
                authComponent = null
                DaggerHomeComponent.builder()
                    .mainActivityComponent(mainComponent)
                    .build()
                    .inject(fragment)
            }
            is NotesFragment -> {
                DaggerNotesComponent.builder()
                    .mainActivityComponent(mainComponent)
                    .build()
                    .inject(fragment)
            }
            is SettingsFragment -> {
                DaggerSettingsComponent.builder()
                    .mainActivityComponent(mainComponent)
                    .build()
                    .inject(fragment)
            }
            is LearnFragment -> {
                DaggerLearnComponent.builder()
                    .mainActivityComponent(mainComponent)
                    .build()
                    .inject(fragment)
            }
        }
    }

    private fun getAuthComponent(mainComponent: MainActivityComponent): AuthComponent {
        return authComponent ?: DaggerAuthComponent.builder()
            .mainActivityComponent(mainComponent)
            .build().also { authComponent = it }
    }
}