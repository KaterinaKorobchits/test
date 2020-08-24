package my.luckydog.presentation.core.sound

import android.content.res.AssetFileDescriptor
import androidx.annotation.RawRes
import io.reactivex.Single
import java.io.FileDescriptor

interface SoundHelper {

    fun load(@RawRes resId: Int): Single<Unit>

    fun load(path: String): Single<Unit>

    fun load(afd: AssetFileDescriptor): Single<Unit>

    fun load(fd: FileDescriptor, offset: Long, length: Long): Single<Unit>

    fun playBuilder(@RawRes resId: Int): PlayBuilder

    fun playBuilder(path: String): PlayBuilder

    fun playBuilder(afd: AssetFileDescriptor): PlayBuilder

    fun playBuilder(fd: FileDescriptor, offset: Long, length: Long): PlayBuilder

    fun stop(@RawRes resId: Int)

    fun stop(path: String)

    fun stop(afd: AssetFileDescriptor)

    fun stop(fd: FileDescriptor, offset: Long, length: Long)

    fun pause(@RawRes resId: Int)

    fun pause(path: String)

    fun pause(afd: AssetFileDescriptor)

    fun pause(fd: FileDescriptor, offset: Long, length: Long)

    fun resume(@RawRes resId: Int)

    fun resume(path: String)

    fun resume(afd: AssetFileDescriptor)

    fun resume(fd: FileDescriptor, offset: Long, length: Long)

    fun release()
}