package my.luckydog.di.app

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.BindsInstance
import dagger.Component
import my.luckydog.App
import my.luckydog.boundaries.app.auth.AuthStore
import my.luckydog.boundaries.app.config.RemoteConfigStore
import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.data.core.translate.TranslateCache
import my.luckydog.di.app.AppModule.Companion.APP_CONTEXT
import my.luckydog.navigation.NavigatorBinder
import my.luckydog.navigation.NavigatorProducer
import my.luckydog.presentation.core.keyboard.KeyboardDetector
import my.luckydog.presentation.core.network.NetworkDetector
import my.luckydog.presentation.core.theme.ThemeStore
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class])
interface AppComponent {

    fun inject(app: App)

    @Named(APP_CONTEXT)
    fun getAppContext(): Context

    fun getResources(): Resources

    fun getAuthStore(): AuthStore

    fun getSessionStore(): SessionStore

    fun getThemeStore(): ThemeStore

    fun navigatorBinder(): NavigatorBinder

    fun navigatorProducer(): NavigatorProducer

    fun keyboardDetector(): KeyboardDetector

    fun networkDetector(): NetworkDetector

    fun remoteConfigStore(): RemoteConfigStore

    fun translateCache(): TranslateCache

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}