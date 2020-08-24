package my.luckydog.drawer

import android.view.View
import androidx.navigation.NavController

interface DrawerBinder {

    fun bind(view: View, navController: NavController)
}