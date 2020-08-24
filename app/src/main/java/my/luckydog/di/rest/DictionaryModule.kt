package my.luckydog.di.rest

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.BuildConfig
import my.luckydog.boundaries.core.dictionary.DictionaryRepository
import my.luckydog.boundaries.core.dictionary.entities.DicEntry
import my.luckydog.boundaries.core.dictionary.entities.Example
import my.luckydog.boundaries.core.dictionary.entities.Translation
import my.luckydog.common.Mapper
import my.luckydog.data.core.dictionary.DictionaryApi
import my.luckydog.data.core.dictionary.DictionaryRepositoryImpl
import my.luckydog.data.core.dictionary.entities.ResponseDicEntry
import my.luckydog.data.core.dictionary.entities.ResponseExample
import my.luckydog.data.core.dictionary.entities.ResponseTranslation
import my.luckydog.data.core.dictionary.mappers.DicEntryMapper
import my.luckydog.data.core.dictionary.mappers.ExampleMapper
import my.luckydog.data.core.dictionary.mappers.TranslationMapper
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
abstract class DictionaryModule {

    @Module
    companion object {
        private const val OKHTTP_DICTIONARY = "okhttp_dictionary"
        private const val INTERCEPTOR_DICTIONARY = "interceptor_dictionary"

        @JvmStatic
        @Provides
        @FragmentScope
        fun provideApi(
            @Named(GSON) gson: Gson,
            @Named(OKHTTP_DICTIONARY) okHttpClient: OkHttpClient
        ): DictionaryApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.DICTIONARY_URL)
                .client(okHttpClient)
                .build()
            return retrofit.create(DictionaryApi::class.java)
        }

        @JvmStatic
        @Provides
        @FragmentScope
        @Named(OKHTTP_DICTIONARY)
        fun provideOkHttp(
            loggingInterceptor: HttpLoggingInterceptor,
            @Named(INTERCEPTOR_DICTIONARY) requestInterceptor: Interceptor
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
        @Named(INTERCEPTOR_DICTIONARY)
        fun provideRequestInterceptor(): Interceptor {
            return Interceptor {
                val original = it.request()
                val url = original.url.newBuilder()
                    .addQueryParameter(QUERY_KEY, BuildConfig.DICTIONARY_KEY)

                val request = original.newBuilder()
                    .url(url.build())
                    .build()
                return@Interceptor it.proceed(request)
            }
        }
    }

    @Binds
    @FragmentScope
    abstract fun provideRepository(repository: DictionaryRepositoryImpl): DictionaryRepository

    @Binds
    @FragmentScope
    abstract fun provideDicEntryMapper(mapper: DicEntryMapper): Mapper<ResponseDicEntry, DicEntry>

    @Binds
    @FragmentScope
    abstract fun provideTranslationMapper(mapper: TranslationMapper): Mapper<ResponseTranslation, Translation>

    @Binds
    @FragmentScope
    abstract fun provideExampleMapper(mapper: ExampleMapper): Mapper<ResponseExample, Example>
}