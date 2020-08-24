package my.luckydog.presentation.core.keyboard

import android.view.Window
import kotlinx.coroutines.flow.Flow

interface KeyboardDetector {

    fun observe(window: Window): Flow<Boolean>
}