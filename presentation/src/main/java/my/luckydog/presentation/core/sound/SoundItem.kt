package my.luckydog.presentation.core.sound

import android.content.res.AssetFileDescriptor
import androidx.annotation.RawRes
import java.io.FileDescriptor

data class SoundItem(
    @RawRes val rawId: Int? = null,
    val path: String? = null,
    val afd: AssetFileDescriptor? = null,
    val fd: FileDescriptor? = null,
    val fdId: Long? = null,
    var isLoaded: Boolean = false
)