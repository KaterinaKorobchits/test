package my.luckydog.di.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import my.luckydog.App
import my.luckydog.boundaries.auth.AuthStore
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.data.auth.AuthStoreImpl
import my.luckydog.data.session.SessionStoreImpl
import my.luckydog.data.utils.PropertiesStoreImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        const val APP_CONTEXT = "app_context"
        private const val SESSION_PREF = "session_pref"
        private const val AUTH_PREF = "auth_pref"
    }

    @Provides
    @Singleton
    @Named(APP_CONTEXT)
    fun provideAppContext(app: Application): Context = app

    @Provides
    @Singleton
    fun provideResources(app: Application): Resources = app.resources

    @Provides
    @Singleton
    @Named(AUTH_PREF)
    fun provideAuthSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(AUTH_PREF, AppCompatActivity.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthStore(@Named(AUTH_PREF) authPref: SharedPreferences): AuthStore {
        return AuthStoreImpl(PropertiesStoreImpl(authPref))
    }

    @Provides
    @Singleton
    @Named(SESSION_PREF)
    fun provideSessionSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(SESSION_PREF, AppCompatActivity.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSessionStore(@Named(SESSION_PREF) sessionPref: SharedPreferences): SessionStore {
        return SessionStoreImpl(PropertiesStoreImpl(sessionPref))
    }
}