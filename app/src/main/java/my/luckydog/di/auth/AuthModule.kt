package my.luckydog.di.auth

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.data.app.db.LuckyDogDatabase
import my.luckydog.data.app.db.user.UserDao
import my.luckydog.di.app.AppModule.Companion.APP_CONTEXT
import my.luckydog.di.scopes.AuthScope
import my.luckydog.domain.app.validators.EmailCase
import my.luckydog.domain.app.validators.EmailCaseImpl
import my.luckydog.domain.app.validators.PasswordCase
import my.luckydog.domain.app.validators.PasswordCaseImpl
import javax.inject.Named

@Module
abstract class AuthModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @AuthScope
        fun provideDao(@Named(APP_CONTEXT) context: Context): UserDao {
            println("!!!dagger: provideDao")
            return LuckyDogDatabase.getDatabase(context).userDao()
        }
    }

    @Binds
    @AuthScope
    abstract fun provideEmailCase(case: EmailCaseImpl): EmailCase

    @Binds
    @AuthScope
    abstract fun providePasswordCase(case: PasswordCaseImpl): PasswordCase
}