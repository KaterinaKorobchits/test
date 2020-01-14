package my.luckydog.di.app

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.BindsInstance
import dagger.Component
import my.luckydog.App
import my.luckydog.boundaries.auth.AuthStore
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.di.app.AppModule.Companion.APP_CONTEXT
import my.luckydog.navigation.NavigatorBinder
import my.luckydog.navigation.NavigatorProducer
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

    fun navigatorBinder(): NavigatorBinder

    fun navigatorProducer(): NavigatorProducer

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}