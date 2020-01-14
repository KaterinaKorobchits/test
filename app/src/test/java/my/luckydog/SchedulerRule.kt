package my.luckydog

/*import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.LazyThreadSafetyMode.NONE

class SchedulerRule : TestRule {

    private val scheduler by lazy(NONE) {
        Schedulers.trampoline()
    }

    override fun apply(base: Statement, description: Description?) = object : Statement() {

        override fun evaluate() {
            RxJavaPlugins.setIoSchedulerHandler { scheduler }
            RxJavaPlugins.setComputationSchedulerHandler { scheduler }
            RxJavaPlugins.setNewThreadSchedulerHandler { scheduler }
            RxAndroidPlugins.setMainThreadSchedulerHandler { scheduler }

            try {
                base.evaluate()
            } finally {
                RxJavaPlugins.reset()
                RxAndroidPlugins.reset()
            }
        }
    }
}*/