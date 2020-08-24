package my.luckydog

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.CONNECTED
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.facebook.drawee.backends.pipeline.Fresco
import io.fabric.sdk.android.Fabric
import my.luckydog.common.HasWorkerInjector
import my.luckydog.common.WorkerInjector
import my.luckydog.di.app.AppComponent
import my.luckydog.di.app.DaggerAppComponent
import my.luckydog.di.workers.WorkerInjectorImpl
import my.luckydog.presentation.workers.TestWorker
import java.util.concurrent.TimeUnit

class App : Application(), HasAppComponent, HasWorkerInjector {

    companion object {
        private lateinit var appComponent: AppComponent
        private val workerInjector: WorkerInjector by lazy { WorkerInjectorImpl() }
    }

    override fun onCreate() {
        super.onCreate()
        configCrashlytics()
        Fresco.initialize(this)

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        remoteWork()
    }

    override fun getAppComponent(): AppComponent = appComponent

    override fun getWorkerInjector(): WorkerInjector = workerInjector

    private fun configCrashlytics() {
        val crashAnalyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()

        Fabric.with(this, crashAnalyticsKit)
    }

    private fun remoteWork() {
        val constraints = Constraints.Builder().setRequiredNetworkType(CONNECTED).build()
        val work = PeriodicWorkRequestBuilder<TestWorker>(3, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("RemoteWork", KEEP, work)
    }
}