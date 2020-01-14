package my.luckydog.di.activity

import android.content.Context
import android.content.res.Resources
import androidx.navigation.NavController
import dagger.Component
import my.luckydog.activity.MainActivity
import my.luckydog.boundaries.auth.AuthStore
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.di.app.AppComponent
import my.luckydog.di.app.AppModule.Companion.APP_CONTEXT
import my.luckydog.navigation.NavigatorProducer
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
}