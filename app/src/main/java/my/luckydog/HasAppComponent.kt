package my.luckydog

import my.luckydog.di.app.AppComponent

interface HasAppComponent {

    fun getAppComponent(): AppComponent
}