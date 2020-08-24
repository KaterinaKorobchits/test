package my.luckydog.di.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.security.keystore.KeyProperties
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.boundaries.app.auth.AuthStore
import my.luckydog.boundaries.app.config.RemoteConfigStore
import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.data.app.auth.AuthStoreImpl
import my.luckydog.data.app.config.RemoteConfigStoreImpl
import my.luckydog.data.app.session.SessionStoreImpl
import my.luckydog.data.core.PropertiesStoreImpl
import my.luckydog.data.core.translate.TranslateCache
import my.luckydog.presentation.core.keyboard.KeyboardDetector
import my.luckydog.presentation.core.keyboard.KeyboardDetectorImpl
import my.luckydog.presentation.core.network.NetworkDetector
import my.luckydog.presentation.core.network.NetworkDetectorImpl
import my.luckydog.presentation.core.theme.ThemeStore
import javax.crypto.KeyGenerator
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Module
    companion object {
        const val APP_CONTEXT = "app_context"
        private const val SESSION_PREF = "session_pref"
        private const val AUTH_PREF = "auth_pref"
        private const val THEME_PREF = "theme_pref"

        @JvmStatic
        @Provides
        @Singleton
        @Named(APP_CONTEXT)
        fun provideAppContext(app: Application): Context {
            println("!!!dagger: provideAppContext")
            return app
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideResources(app: Application): Resources {
            println("!!!dagger: provideResources")
            return app.resources
        }

        @JvmStatic
        @Provides
        @Singleton
        @Named(AUTH_PREF)
        fun provideAuthSharedPreferences(app: Application): SharedPreferences {
            println("!!!dagger: provideAuthSharedPreferences")
           // return app.getSharedPreferences(AUTH_PREF, AppCompatActivity.MODE_PRIVATE)
            return EncryptedSharedPreferences.create(
                AUTH_PREF,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                app.applicationContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideAuthStore(@Named(AUTH_PREF) authPref: SharedPreferences): AuthStore {
            println("!!!dagger: provideAuthStore")
            return AuthStoreImpl(PropertiesStoreImpl(authPref))
        }

        @JvmStatic
        @Provides
        @Singleton
        @Named(SESSION_PREF)
        fun provideSessionSharedPreferences(app: Application): SharedPreferences {
            println("!!!dagger: provideSessionSharedPreferences")
               // return app.getSharedPreferences(SESSION_PREF, AppCompatActivity.MODE_PRIVATE)
            return EncryptedSharedPreferences.create(
                SESSION_PREF,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                app.applicationContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideSessionStore(@Named(SESSION_PREF) sessionPref: SharedPreferences): SessionStore {
            println("!!!dagger: provideSessionStore")
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            return SessionStoreImpl(PropertiesStoreImpl(sessionPref))
        }

        @JvmStatic
        @Provides
        @Singleton
        @Named(THEME_PREF)
        fun provideThemeSharedPreferences(app: Application): SharedPreferences {
            println("!!!dagger: provideThemeSharedPreferences")
            return app.getSharedPreferences(THEME_PREF, AppCompatActivity.MODE_PRIVATE)
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideThemeStore(@Named(THEME_PREF) themePref: SharedPreferences): ThemeStore {
            println("!!!dagger: provideThemeStore")
            return ThemeStore(PropertiesStoreImpl(themePref))
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideTranslateCache() = TranslateCache(100)
    }

    @Binds
    @Singleton
    abstract fun provideKeyboardDetector(keyboardDetector: KeyboardDetectorImpl): KeyboardDetector

    @Binds
    @Singleton
    abstract fun provideNetworkDetector(networkDetector: NetworkDetectorImpl): NetworkDetector

    @Binds
    @Singleton
    abstract fun provideRemoteConfigStore(store: RemoteConfigStoreImpl): RemoteConfigStore
}