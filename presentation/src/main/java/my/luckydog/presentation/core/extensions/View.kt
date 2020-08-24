package my.luckydog.presentation.core.extensions

import android.view.LayoutInflater
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.luckydog.presentation.core.SafeClickListener
import my.luckydog.presentation.core.behavior.BottomNavigationBehavior

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    setOnClickListener(SafeClickListener { onSafeClick(it) })
}

fun View.inflater(): LayoutInflater = LayoutInflater.from(context)

fun View.disableLayoutBehaviour() {
    val param: CoordinatorLayout.LayoutParams = layoutParams as CoordinatorLayout.LayoutParams
    param.behavior = null
}

fun BottomNavigationView.enableLayoutBehaviour() {
    (layoutParams as CoordinatorLayout.LayoutParams).behavior = BottomNavigationBehavior()
}

