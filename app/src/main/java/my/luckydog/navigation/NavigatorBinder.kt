package my.luckydog.navigation

import androidx.navigation.NavController

interface NavigatorBinder {

    fun bind(controller: NavController)

    fun unbind()
}