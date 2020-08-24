package my.luckydog.presentation.core.behavior

import android.view.Gravity
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar

class BottomNavigationBehavior: CoordinatorLayout.Behavior<View>() {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean = axes == ViewCompat.SCROLL_AXIS_VERTICAL

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = maxOf(0f, minOf(child.height.toFloat(), child.translationY + dy))
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency is Snackbar.SnackbarLayout) updateSnackbar(child, dependency)
        return super.layoutDependsOn(parent, child, dependency)
    }

    private fun updateSnackbar(child: View, snackbar: Snackbar.SnackbarLayout) {
        if (snackbar.layoutParams is CoordinatorLayout.LayoutParams)
            snackbar.layoutParams.apply {
                this as CoordinatorLayout.LayoutParams
                anchorId = child.id
                anchorGravity = Gravity.TOP
                gravity = Gravity.TOP
            }
    }
}