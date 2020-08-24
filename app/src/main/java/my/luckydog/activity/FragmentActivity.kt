package my.luckydog.activity

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment

fun FragmentActivity.findNavGraph(@IdRes viewId: Int): NavGraph {
    return findNavController(viewId).graph
}

fun FragmentActivity.findNavController(@IdRes viewId: Int): NavController {
    return (supportFragmentManager.findFragmentById(viewId) as NavHostFragment).navController
}