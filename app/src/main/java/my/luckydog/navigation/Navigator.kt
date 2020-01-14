package my.luckydog.navigation

import androidx.navigation.NavController
import java.lang.ref.WeakReference

class Navigator : NavigatorBinder, NavigatorProducer {

    private var controller = WeakReference<NavController>(null)

    override fun bind(controller: NavController) {
        this.controller = WeakReference(controller)
    }

    override fun unbind() = controller.clear()

    override fun provide() = controller.get()
}