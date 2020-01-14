package my.luckydog.activity

import androidx.fragment.app.Fragment
import my.luckydog.di.activity.MainActivityComponent

interface MainInjector {

    fun inject(fragment: Fragment, mainComponent: MainActivityComponent)
}