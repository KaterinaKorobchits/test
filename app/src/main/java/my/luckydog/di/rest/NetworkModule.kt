package my.luckydog.di.rest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import my.luckydog.BuildConfig
import my.luckydog.di.scopes.FragmentScope
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import javax.inject.Named

@Module
class NetworkModule {

    companion object {
        const val GSON = "gson"
        const val GSON_NULL = "gson_null"
    }

    @Provides
    @FragmentScope
    @Named(GSON)
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @FragmentScope
    @Named(GSON_NULL)
    fun provideGsonNull(): Gson = GsonBuilder()
        .serializeNulls()
        .create()

    @Provides
    @FragmentScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
    }
}