package my.luckydog.di.activity

import android.content.Context
import android.content.res.Resources
import dagger.Component
import my.luckydog.activity.MainActivity
import my.luckydog.boundaries.app.auth.AuthStore
import my.luckydog.boundaries.app.config.RemoteConfigStore
import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.data.core.translate.TranslateCache
import my.luckydog.di.app.AppComponent
import my.luckydog.di.app.AppModule.Companion.APP_CONTEXT
import my.luckydog.di.scopes.ActivityScope
import my.luckydog.navigation.NavigatorProducer
import my.luckydog.presentation.core.DrawerManager
import my.luckydog.presentation.core.keyboard.KeyboardDetector
import my.luckydog.presentation.core.theme.ThemeRepository
import my.luckydog.presentation.dialogs.DialogManager
import javax.inject.Named

@ActivityScope
@Component(
    modules = [MainActivityModule::class, DialogsModule::class],
    dependencies = [AppComponent::class]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    @Named(APP_CONTEXT)
    fun getAppContext(): Context

    fun getResources(): Resources

    fun getDialogManager(): DialogManager

    fun getSessionStore(): SessionStore

    fun getAuthStore(): AuthStore

    fun navigatorProducer(): NavigatorProducer

    fun keyboardDetector(): KeyboardDetector

    fun drawerManager(): DrawerManager

    fun remoteConfigStore(): RemoteConfigStore

    fun translateCache(): TranslateCache

    fun themeRepository(): ThemeRepository
}