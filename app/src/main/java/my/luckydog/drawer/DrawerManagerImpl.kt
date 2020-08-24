package my.luckydog.drawer

import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import my.luckydog.R
import my.luckydog.databinding.NavigationHeaderBinding
import my.luckydog.presentation.core.DrawerManager

class DrawerManagerImpl : DrawerBinder, DrawerManager {

    private lateinit var headerForm: DrawerHeaderForm
    private lateinit var drawerLayout: DrawerLayout

    override fun bind(view: View, navController: NavController) {
        drawerLayout = view.findViewById(R.id.drawer_layout)
        val navView = drawerLayout.findViewById<NavigationView>(R.id.nav_view)

        navView.setupWithNavController(navController)
        val headerBinding =
            DataBindingUtil.getBinding<NavigationHeaderBinding>(navView.getHeaderView(0))
        headerForm = headerBinding?.form ?: DrawerHeaderForm()
    }

    override fun setEmai(email: String) = headerForm.email.set(email)

    override fun isOpen(): Boolean = drawerLayout.isDrawerOpen(GravityCompat.START)

    override fun close() {
        if (isOpen()) drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun open() {
        if (!isOpen()) drawerLayout.openDrawer(GravityCompat.START)
    }
}