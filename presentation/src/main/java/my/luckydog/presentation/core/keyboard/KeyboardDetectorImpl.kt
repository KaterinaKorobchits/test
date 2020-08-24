package my.luckydog.presentation.core.keyboard

import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class KeyboardDetectorImpl @Inject constructor() : KeyboardDetector {

    private companion object {
        private const val MIN_KEYBOARD_HEIGHT_RATIO = 0.15
    }

    init {
        println("!!!init: KeyboardDetectorImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun observe(window: Window): Flow<Boolean> {
        val rootView = window.findViewById<View>(android.R.id.content)
        val windowHeight = DisplayMetrics().let {
            window.windowManager.defaultDisplay.getMetrics(it)
            it.heightPixels
        }
        val rect = Rect()

        return callbackFlow {
            val listener = ViewTreeObserver.OnGlobalLayoutListener {
                rootView.getWindowVisibleDisplayFrame(rect)
                val keyboardHeight = windowHeight - rect.height()
                offer(keyboardHeight > windowHeight * MIN_KEYBOARD_HEIGHT_RATIO)
            }

            rootView.run {
                viewTreeObserver.addOnGlobalLayoutListener(listener)

                awaitClose { viewTreeObserver.removeOnGlobalLayoutListener(listener) }
            }
        }.distinctUntilChanged()
    }
}

/*class KeyboardDetectorImpl @Inject constructor() : KeyboardDetector {

    private companion object {
        private const val MIN_KEYBOARD_HEIGHT_RATIO = 0.15
    }

    init {
        println("!!!init: KeyboardDetectorImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun observe(window: Window): Observable<Boolean> {
        val rootView = window.findViewById<View>(android.R.id.content)
        val windowHeight = DisplayMetrics().let {
            window.windowManager.defaultDisplay.getMetrics(it)
            it.heightPixels
        }
        val rect = Rect()

        return Observable.create<Boolean> { emitter ->
            val listener = ViewTreeObserver.OnGlobalLayoutListener {
                rootView.getWindowVisibleDisplayFrame(rect)
                val keyboardHeight = windowHeight - rect.height()
                emitter.onNext(keyboardHeight > windowHeight * MIN_KEYBOARD_HEIGHT_RATIO)
            }

            rootView.run {
                viewTreeObserver.addOnGlobalLayoutListener(listener)

                emitter.setCancellable { viewTreeObserver.removeOnGlobalLayoutListener(listener) }
            }
        }.distinctUntilChanged()
    }
}*/