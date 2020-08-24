package my.luckydog.activity

import androidx.databinding.ObservableBoolean

data class MainForm(
    val drawerIsLock: ObservableBoolean =  ObservableBoolean(),
    val bottomNavIsVisible: ObservableBoolean =  ObservableBoolean(),
    val appBarIsVisible: ObservableBoolean =  ObservableBoolean(),
    val fabIsVisible: ObservableBoolean = ObservableBoolean(),
    val isFragmentScroll: ObservableBoolean =  ObservableBoolean()
) {
    internal fun hideNavigation() = setupNavigation(false)

    internal fun showNavigation() = setupNavigation(true)

    private fun setupNavigation(isShow: Boolean) {
        drawerIsLock.set(!isShow)
        bottomNavIsVisible.set(isShow)
        appBarIsVisible.set(isShow)
        fabIsVisible.set(isShow)
        isFragmentScroll.set(isShow)
    }
}