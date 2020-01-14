package my.luckydog

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import io.fabric.sdk.android.Fabric
import my.luckydog.di.app.AppComponent
import my.luckydog.di.app.AppModule
import my.luckydog.di.app.DaggerAppComponent

class App : Application(), HasAppComponent {

    companion object {
        private lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        configCrashlytics()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun getAppComponent(): AppComponent = appComponent

    private fun configCrashlytics() {
        val crashAnalyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()

        Fabric.with(this, crashAnalyticsKit)
    }
}