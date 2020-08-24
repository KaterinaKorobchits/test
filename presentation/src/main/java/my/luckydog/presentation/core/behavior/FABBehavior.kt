package my.luckydog.presentation.core.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

class FABBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean = dependency is Snackbar.SnackbarLayout

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
        child.translationY = 0f
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean = updateButton(child, dependency)

    private fun updateButton(child: View, dependency: View): Boolean {
        return if (dependency is Snackbar.SnackbarLayout) {
            val oldTranslation = child.translationY
            val newTranslation = dependency.run { translationY - height.toFloat() }
            child.translationY = newTranslation

            oldTranslation != newTranslation
        } else false
    }
}