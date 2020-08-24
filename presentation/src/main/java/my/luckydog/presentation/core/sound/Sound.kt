package my.luckydog.presentation.core.sound

import android.content.res.AssetFileDescriptor
import androidx.annotation.RawRes
import java.io.FileDescriptor

data class Sound(
    @RawRes val rawId: Int? = null,
    val path: String? = null,
    val afd: AssetFileDescriptor? = null,
    val fd: FileDescriptor? = null,
    val fdId: Long? = null,
    val loadId: Int,
    var streamId: Int = -1,
    var isLoaded: Boolean = false
)