package my.luckydog.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import my.luckydog.HasAppComponent
import my.luckydog.R
import my.luckydog.databinding.ActivityMainBinding
import my.luckydog.databinding.NavigationHeaderBinding
import my.luckydog.di.activity.DaggerMainActivityComponent
import my.luckydog.drawer.DrawerBinder
import my.luckydog.drawer.DrawerHeaderForm
import my.luckydog.navigation.NavigatorBinder
import my.luckydog.presentation.core.DrawerManager
import my.luckydog.presentation.core.extensions.disableLayoutBehaviour
import my.luckydog.presentation.core.extensions.enableLayoutBehaviour
import my.luckydog.presentation.core.extensions.getViewModel
import my.luckydog.presentation.core.extensions.hideSoftKeyboard
import my.luckydog.presentation.core.network.NetworkDetector
import my.luckydog.presentation.core.theme.ThemeRepository
import my.luckydog.presentation.fragments.home.HomeFragment
import my.luckydog.presentation.fragments.learn.LearnFragment
import my.luckydog.presentation.fragments.signin.SignInFragment
import my.luckydog.presentation.fragments.signup.SignUpFragment
import my.luckydog.presentation.fragments.splash.SplashFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var injector: MainInjector

    @Inject
    lateinit var navigatorBinder: NavigatorBinder

    @Inject
    lateinit var drawerBinder: DrawerBinder

    @Inject
    lateinit var drawerManager: DrawerManager

    @Inject
    lateinit var network: NetworkDetector

    @Inject
    lateinit var theme: ThemeRepository

    private val viewModel: MainViewModel by lazy {
        getViewModel {
            MainViewModel(
                DaggerMainActivityComponent.builder()
                    .appComponent((applicationContext as HasAppComponent).getAppComponent())
                    .build()
            )
        }
    }

    private val mainForm = MainForm()

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
            injector.inject(f, viewModel.component)
        }

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            when (f) {
                is NavHostFragment -> return
                is SignInFragment, is SignUpFragment, is SplashFragment -> mainForm.hideNavigation()
                else -> mainForm.showNavigation()
            }
            if (f is LearnFragment) bottom_nav.disableLayoutBehaviour()
            else bottom_nav.enableLayoutBehaviour()
        }

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            if (f is HomeFragment)
                findNavGraph(R.id.nav_host_fragment).startDestination = R.id.home
            else if (f is SignInFragment || f is SignUpFragment)
                findNavGraph(R.id.nav_host_fragment).startDestination = R.id.splash
        }

        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
            f.hideSoftKeyboard()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.component.inject(this)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, true)

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater).apply { form = mainForm }
        NavigationHeaderBinding.bind(binding.navView.getHeaderView(0)).form = DrawerHeaderForm()

        theme.apply(this)
        setContentView(binding.root)

        //drawerBinder.bind(drawer_layout, findNavController(R.id.nav_host_fragment))

        setSupportActionBar(toolbar)

        /*nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
        bottom_nav.setupWithNavController(findNavController(R.id.nav_host_fragment))
        toolbar.setupWithNavController(
            findNavController(R.id.nav_host_fragment),
            AppBarConfiguration(setOf(R.id.home), drawer_layout)
        )*/
        setupBottomNavigationBar()

        network.observe(this)
            .onEach { println(it) }
            .launchIn(lifecycle.coroutineScope)
    }

    private val navGraphIds =
        listOf(R.navigation.start, R.navigation.learn, R.navigation.vocabulary, R.navigation.notes)
    private val helper by lazy {
        NavigatiionHelper(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )
    }

    private fun setupBottomNavigationBar() {

        helper.setupWithNavController(bottom_nav)
        helper.currentNavController.observe(this, Observer { navController ->
            setupActionBarWithNavController(
                navController,
                AppBarConfiguration(setOf(R.id.start), drawer_layout)
            )
            nav_view.setupWithNavController(navController)
            navigatorBinder.bind(navController)
        })
    }

    override fun onSupportNavigateUp(): Boolean = helper.onSupportNavigateUp()

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

    override fun onBackPressed() {
        /*if (drawerManager.isOpen()) drawerManager.close() else*/ super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        supportFragmentManager.fragments.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
