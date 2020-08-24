package my.luckydog.navigation

import my.luckydog.R
import my.luckydog.presentation.fragments.learn.LearnNavigation
import javax.inject.Inject

class LearnNavigationImpl @Inject constructor(private val producer: NavigatorProducer) :
    LearnNavigation {

    init {
        println("!!!init: SignInNavigationImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun showTest() {
        producer.provide()?.navigate(R.id.learn_to_test)
    }
}