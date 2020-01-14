package my.luckydog.activity

import androidx.fragment.app.Fragment
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.di.home.DaggerHomeComponent
import my.luckydog.di.signin.DaggerSignInComponent
import my.luckydog.di.signup.DaggerSignUpComponent
import my.luckydog.di.splash.DaggerSplashComponent
import my.luckydog.presentation.fragments.home.HomeFragment
import my.luckydog.presentation.fragments.signin.SignInFragment
import my.luckydog.presentation.fragments.signup.SignUpFragment
import my.luckydog.presentation.fragments.splash.SplashFragment

class MainInjectorImpl : MainInjector {

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
                    .mainActivityComponent(mainComponent)
                    .build()
                    .inject(fragment)
            }
            is SignInFragment -> {
                DaggerSignInComponent.builder()
                    .mainActivityComponent(mainComponent)
                    .build()
                    .inject(fragment)
            }
            is HomeFragment -> {
                DaggerHomeComponent.builder()
                    .mainActivityComponent(mainComponent)
                    .build()
                    .inject(fragment)
            }
        }
    }
}