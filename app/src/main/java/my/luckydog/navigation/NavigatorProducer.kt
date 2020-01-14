package my.luckydog.navigation

import androidx.navigation.NavController

interface NavigatorProducer {

    fun provide(): NavController?
}