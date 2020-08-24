package my.luckydog.presentation.core.network

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface NetworkDetector {

    fun observe(context: Context): Flow<Boolean>
}