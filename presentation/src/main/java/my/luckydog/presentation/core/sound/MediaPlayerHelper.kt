package my.luckydog.presentation.core.sound

import android.app.Application
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.Subject
import java.io.FileDescriptor

class MediaPlayerHelper(
    private val context: Context,
    private val attrs: AudioAttributes
) : SoundHelper {

    private val mediaPlayer: MediaPlayer = MediaPlayer().apply { setAudioAttributes(attrs) }

    private lateinit var subject: Subject<Unit>
    private var sound: SoundItem? = null

    private val prepareListener = MediaPlayer.OnPreparedListener {
        sound?.isLoaded = true
        subject.onNext(Unit)
        subject.onComplete()
    }

    private val errorListener = MediaPlayer.OnErrorListener { _, _, _ ->
        val source = sound?.rawId ?: sound?.path ?: sound?.afd ?: sound?.fd
        subject.onError(LoadSoundFail(source))
        false
    }

    private val completionListener = MediaPlayer.OnCompletionListener {
        counterRepeat++
        if (counterRepeat <= repeat) it.start()
    }

    init {
        mediaPlayer.run {
            setOnPreparedListener(prepareListener)
            setOnErrorListener(errorListener)
            setOnCompletionListener(completionListener)
        }
    }

    private var repeat: Int = 0
        set(value) {
            field = value
            counterRepeat = 0
        }
    private var counterRepeat: Int = 0

    override fun load(resId: Int): Single<Unit> {
        sound?.let { if (it.rawId == resId) alreadyExist(it) }
        mediaPlayer.reset()
        return try {
            val afd = (context as Application).resources.openRawResourceFd(resId)
            sound = SoundItem(resId)
            mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mediaPlayer.prepareAsync()
            AsyncSubject.create<Unit>().also { subject = it }.first(Unit)
        } catch (e: Exception) {
            mediaPlayer.release()
            Single.error(LoadSoundFail(resId))
        }
    }

    override fun load(path: String): Single<Unit> {
        sound?.let { if (it.path == path) alreadyExist(it) }
        mediaPlayer.reset()
        sound = SoundItem(path = path)
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepareAsync()
        return AsyncSubject.create<Unit>().also { subject = it }.first(Unit)
    }

    override fun load(afd: AssetFileDescriptor): Single<Unit> {
        sound?.let { if (it.afd == afd) return alreadyExist(it) }
        mediaPlayer.reset()
        sound = SoundItem(afd = afd)
        mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mediaPlayer.prepareAsync()
        return AsyncSubject.create<Unit>().also { subject = it }.first(Unit)
    }

    override fun load(fd: FileDescriptor, offset: Long, length: Long): Single<Unit> {
        val id = getId(fd, offset, length)
        sound?.let { if (it.fdId == id) return alreadyExist(it) }
        mediaPlayer.reset()
        sound = SoundItem(fd = fd, fdId = id)
        mediaPlayer.setDataSource(fd, offset, length)
        mediaPlayer.prepareAsync()
        return AsyncSubject.create<Unit>().also { subject = it }.first(Unit)
    }

    private fun alreadyExist(sound: SoundItem) = if (sound.isLoaded) Single.just(Unit) else {
        if (!subject.hasComplete()) subject.first(Unit) else
            throw LoadSoundFail(sound.rawId ?: sound.path ?: sound.fd ?: sound.afd)
    }

    override fun playBuilder(resId: Int): PlayBuilder = PlayBuilderImpl(resId)

    override fun playBuilder(path: String): PlayBuilder = PlayBuilderImpl(path)

    override fun playBuilder(afd: AssetFileDescriptor): PlayBuilder = PlayBuilderImpl(afd)

    override fun playBuilder(fd: FileDescriptor, offset: Long, length: Long): PlayBuilder {
        return PlayBuilderImpl(fd, offset, length)
    }

    override fun stop(resId: Int) {
        sound?.run { stop(rawId == resId) }
    }

    override fun stop(path: String) {
        sound?.run { stop(this.path == path) }
    }

    override fun stop(afd: AssetFileDescriptor) {
        sound?.run { stop(this.afd == afd) }
    }

    override fun stop(fd: FileDescriptor, offset: Long, length: Long) {
        sound?.run { stop(fdId == getId(fd, offset, length)) }
    }

    override fun pause(resId: Int) {
        sound?.run { pause(rawId == resId) }
    }

    override fun pause(path: String) {
        sound?.run { pause(this.path == path) }
    }

    override fun pause(afd: AssetFileDescriptor) {
        sound?.run { pause(this.afd == afd) }
    }

    override fun pause(fd: FileDescriptor, offset: Long, length: Long) {
        sound?.run { pause(fdId == getId(fd, offset, length)) }
    }

    override fun resume(resId: Int) {
        sound?.run { resume(rawId == resId) }
    }

    override fun resume(path: String) {
        sound?.run { resume(this.path == path) }
    }

    override fun resume(afd: AssetFileDescriptor) {
        sound?.run { resume(this.afd == afd) }
    }

    override fun resume(fd: FileDescriptor, offset: Long, length: Long) {
        sound?.run { resume(fdId == getId(fd, offset, length)) }
    }

    override fun release() {
        mediaPlayer.release()
        sound = null
        if (!subject.hasComplete()) subject.onComplete()
    }

    private fun getId(descriptor: FileDescriptor, offset: Long, length: Long): Long {
        return 2 * descriptor.hashCode() + 3 * offset + 5 * length
    }

    private fun stop(idExpr: Boolean) {
        if (idExpr && mediaPlayer.isPlaying) mediaPlayer.stop()
    }

    private fun pause(idExpr: Boolean) {
        if (idExpr && mediaPlayer.isPlaying) mediaPlayer.pause()
    }

    private fun resume(idExpr: Boolean) {
        sound?.run { if (idExpr && isLoaded && !mediaPlayer.isPlaying) mediaPlayer.start() }
    }

    private inner class PlayBuilderImpl private constructor() : PlayBuilder {

        private var rawId: Int? = null
        private var path: String? = null
        private var afd: AssetFileDescriptor? = null
        private var fd: FileDescriptor? = null
        private var fdId: Long? = null

        private var leftVolume: Float = 1f
        private var rightVolume: Float = 1f
        private var repeat: Int = 0
        private var looping: Boolean = false
        private var rate: Float = 0f

        constructor(resId: Int) : this() {
            rawId = resId
        }

        constructor(path: String) : this() {
            this.path = path
        }

        constructor(afd: AssetFileDescriptor) : this() {
            this.afd = afd
        }

        constructor(fd: FileDescriptor, offset: Long, length: Long) : this() {
            this.fd = fd
            fdId = getId(fd, offset, length)
        }

        override fun volume(leftVolume: Float, rightVolume: Float): PlayBuilder {
            return apply { this.leftVolume = leftVolume; this.rightVolume = rightVolume }
        }

        override fun leftVolume(volume: Float) = apply { leftVolume = volume }

        override fun rightVolume(volume: Float) = apply { rightVolume = volume }

        override fun repeat(count: Int) = apply { repeat = count }

        override fun looping() = apply { looping = true }

        override fun rate(rate: Float) = apply { this.rate = rate }

        override fun play() {

            checkSound(sound, rawId, path, afd, fdId)

            mediaPlayer.run {
                if (isPlaying) {
                    pause()
                    seekTo(0)
                }
                this@MediaPlayerHelper.repeat = repeat
                setVolume(leftVolume, rightVolume)
                isLooping = looping
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    playbackParams = PlaybackParams().setSpeed(rate)
                }
                start()
            }
        }

        @Throws(UnknownSound::class, SoundNotLoaded::class)
        private fun checkSound(
            sound: SoundItem?,
            rawId: Int?,
            path: String?,
            afd: AssetFileDescriptor?,
            fdId: Long?
        ) {
            val isValidSource = sound?.let {
                it.rawId == rawId && it.path == path && it.afd == afd && it.fdId == fdId
            }
            if (isValidSource != true) throw UnknownSound(rawId ?: path ?: afd ?: fd)
            if (!sound.isLoaded) throw SoundNotLoaded(rawId ?: path ?: afd ?: fd)
        }
    }
}