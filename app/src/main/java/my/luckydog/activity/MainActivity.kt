package my.luckydog.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import my.luckydog.HasAppComponent
import my.luckydog.R
import my.luckydog.di.activity.DaggerMainActivityComponent
import my.luckydog.di.activity.MainActivityComponent
import my.luckydog.di.activity.MainActivityModule
import my.luckydog.navigation.NavigatorBinder
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var injector: MainInjector

    @Inject
    lateinit var navigatorBinder: NavigatorBinder

    private lateinit var component: MainActivityComponent

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
            Log.e("aaa", "onFragmentPreAttached - $f")
            injector.inject(f, component)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component = DaggerMainActivityComponent.builder()
            .appComponent((applicationContext as HasAppComponent).getAppComponent())
            .build().also { it.inject(this) }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        navigatorBinder.bind(findNavController(R.id.nav_host_fragment))
    }


    override fun onPause() {
        // lock navigation for hidden app. else - IllegalStateException: Can not perform this action after onSaveInstanceState
        navigatorBinder.unbind()
        super.onPause()
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
        super.onDestroy()
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
}