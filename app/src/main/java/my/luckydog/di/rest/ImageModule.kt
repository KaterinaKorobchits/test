package my.luckydog.di.rest

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.BuildConfig
import my.luckydog.boundaries.core.image.Image
import my.luckydog.boundaries.core.image.ImageRepository
import my.luckydog.boundaries.core.image.ImageStore
import my.luckydog.boundaries.core.image.ResultImage
import my.luckydog.common.Mapper
import my.luckydog.data.core.image.ImageApi
import my.luckydog.data.core.image.ImageRepositoryImpl
import my.luckydog.data.core.image.ImageStoreImpl
import my.luckydog.data.core.image.ResultImageMapper
import my.luckydog.data.core.image.entities.ResponseImages
import my.luckydog.di.rest.NetworkModule.Companion.GSON
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.domain.core.image.GetImagesCaseImpl
import my.luckydog.domain.core.image.ItemImageMapper
import my.luckydog.interactors.core.image.GetImagesCase
import my.luckydog.interactors.core.image.ItemImage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
abstract class ImageModule {

    @Module
    companion object {
        private const val OKHTTP_IMAGE = "okhttp_image"
        private const val INTERCEPTOR_IMAGE = "interceptor_image"

        @JvmStatic
        @Provides
        @FragmentScope
        fun provideApi(
            @Named(GSON) gson: Gson,
            @Named(OKHTTP_IMAGE) okHttpClient: OkHttpClient
        ): ImageApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.IMAGE_URL)
                .client(okHttpClient)
                .build()
            return retrofit.create(ImageApi::class.java)
        }

        @JvmStatic
        @Provides
        @FragmentScope
        @Named(OKHTTP_IMAGE)
        fun provideOkHttp(
            loggingInterceptor: HttpLoggingInterceptor,
            @Named(INTERCEPTOR_IMAGE) requestInterceptor: Interceptor
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
        @Named(INTERCEPTOR_IMAGE)
        fun provideRequestInterceptor(): Interceptor {
            return Interceptor {
                val original = it.request()
                val url = original.url.newBuilder()
                    .addQueryParameter(API_KEY, BuildConfig.IMAGE_KEY)
                    .addQueryParameter(METHOD, METHOD_VALUE)
                    .addQueryParameter(MEDIA, MEDIA_VALUE)
                    .addQueryParameter(EXTRAS, EXTRAS_VALUE)
                    .addQueryParameter(FORMAT, JSON)
                    .addQueryParameter(JSON_CALLBACK, JSON_CALLBACK_VALUE)
                    .addQueryParameter(SAVE_SEARCH, SAVE_SEARCH_VALUE)

                val request = original.newBuilder()
                    .url(url.build())
                    .build()

                return@Interceptor it.proceed(request)
            }
        }
    }

    @Binds
    @FragmentScope
    abstract fun provideRepository(repository: ImageRepositoryImpl): ImageRepository

    @Binds
    @FragmentScope
    abstract fun provideStore(store: ImageStoreImpl): ImageStore

    @Binds
    @FragmentScope
    abstract fun provideGetImagesCase(case: GetImagesCaseImpl): GetImagesCase

    @Binds
    @FragmentScope
    abstract fun provideResultMapper(mapper: ResultImageMapper): Mapper<ResponseImages, ResultImage>

    @Binds
    @FragmentScope
    abstract fun provideItemImageMapper(mapper: ItemImageMapper): Mapper<Image, ItemImage>
}