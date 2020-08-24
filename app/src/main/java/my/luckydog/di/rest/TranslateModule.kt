package my.luckydog.di.rest

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.BuildConfig
import my.luckydog.boundaries.core.translate.TranslateRepository
import my.luckydog.boundaries.core.translate.errors.TranslateErrorHandler
import my.luckydog.data.core.translate.TranslateApi
import my.luckydog.data.core.translate.TranslateErrorHandlerImpl
import my.luckydog.data.core.translate.TranslateRepositoryImpl
import my.luckydog.di.rest.NetworkModule.Companion.GSON
import my.luckydog.di.scopes.FragmentScope
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
abstract class TranslateModule {

    @Module
    companion object {
        private const val OKHTTP_TRANSLATE = "okhttp_translate"
        private const val INTERCEPTOR_TRANSLATE = "interceptor_translate"

        @JvmStatic
        @Provides
        @FragmentScope
        fun provideApi(
            @Named(GSON) gson: Gson,
            @Named(OKHTTP_TRANSLATE) okHttpClient: OkHttpClient
        ): TranslateApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.TRANSLATE_URL)
                .client(okHttpClient)
                .build()
            return retrofit.create(TranslateApi::class.java)
        }

        @JvmStatic
        @Provides
        @FragmentScope
        @Named(OKHTTP_TRANSLATE)
        fun provideOkHttp(
            loggingInterceptor: HttpLoggingInterceptor,
            @Named(INTERCEPTOR_TRANSLATE) requestInterceptor: Interceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestInterceptor)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }

        @JvmStatic
        @Provides
        @FragmentScope
        @Named(INTERCEPTOR_TRANSLATE)
        fun provideRequestInterceptor(): Interceptor {
            return Interceptor {
                val original = it.request()
                val url = original.url.newBuilder()
                    .addQueryParameter(QUERY_KEY, BuildConfig.TRANSLATE_KEY)

                val request = original.newBuilder()
                    .url(url.build())
                    .build()
                return@Interceptor it.proceed(request)
            }
        }
    }

    @Binds
    @FragmentScope
    abstract fun provideTranslateRepository(repository: TranslateRepositoryImpl): TranslateRepository

    @Binds
    @FragmentScope
    abstract fun provideTranslateErrorHandler(errors: TranslateErrorHandlerImpl): TranslateErrorHandler
}