package my.luckydog.di.auth

import android.content.Context
import android.content.res.Resources
import dagger.Component
import my.luckydog.boundaries.app.auth.AuthStore
import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.data.app.db.user.UserDao
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.di.app.AppModule
import my.luckydog.di.scopes.AuthScope
import my.luckydog.domain.app.validators.EmailCase
import my.luckydog.domain.app.validators.PasswordCase
import my.luckydog.navigation.NavigatorProducer
import my.luckydog.presentation.core.keyboard.KeyboardDetector
import my.luckydog.presentation.dialogs.DialogManager
import javax.inject.Named

@AuthScope
@Component(
    modules = [AuthModule::class],
    dependencies = [MainActivityComponent::class]
)
interface AuthComponent {

    fun getEmailCase(): EmailCase

    fun getPasswordCase(): PasswordCase

    fun getUserDao(): UserDao

    @Named(AppModule.APP_CONTEXT)
    fun getAppContext(): Context

    fun getResources(): Resources

    fun getDialogManager(): DialogManager

    fun getSessionStore(): SessionStore

    fun getAuthStore(): AuthStore

    fun navigatorProducer(): NavigatorProducer

    fun keyboardDetector(): KeyboardDetector
}