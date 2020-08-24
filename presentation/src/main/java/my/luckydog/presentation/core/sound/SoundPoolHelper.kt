package my.luckydog.presentation.core.sound

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.SoundPool
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.Subject
import java.io.FileDescriptor
import java.util.*
import android.media.AudioAttributes as AudioAttrs

class SoundPoolHelper(
    private val context: Context,
    maxStreams: Int = MAX_STREAMS_DEF,
    attrs: AudioAttrs = ATTRS_DEF
) : SoundHelper, SoundStreamsHelper {

    private companion object {
        private const val MAX_STREAMS_DEF = 1
        private const val USAGE_DEF = AudioAttrs.USAGE_MEDIA
        private const val CONTENT_TYPE_DEF = AudioAttrs.CONTENT_TYPE_SONIFICATION

        private val ATTRS_DEF = AudioAttrs.Builder()
            .setUsage(USAGE_DEF)
            .setContentType(CONTENT_TYPE_DEF)
            .build()
    }

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(maxStreams)
        .setAudioAttributes(attrs)
        .build()

    private val loadListener = SoundPool.OnLoadCompleteListener { _, sampleId, status ->
        val subject = subjects[sampleId]
        val sound = sounds[sampleId]?.also { sounds[sampleId] = it.copy(isLoaded = true) }

        if (subject != null && !subject.hasComplete()) {
            if (status == 0) subject.apply { onNext(Unit); onComplete() } else {
                val source = sound?.rawId ?: sound?.path ?: sound?.afd ?: sound?.fd
                subject.onError(LoadSoundFail(source))
            }
        }
        subjects.remove(sampleId)
    }

    init {
        soundPool.setOnLoadCompleteListener(loadListener)
    }

    private val subjects = Hashtable<Int, Subject<Unit>>()
    private val sounds = Hashtable<Int, Sound>()

    override fun load(resId: Int): Single<Unit> {
        val sound = byResId(resId)
        return if (sound != null) alreadyExist(sound) else {
            val loadId = soundPool.load(context, resId, 1)
            loadNew(Sound(rawId = resId, loadId = loadId))
        }
    }

    override fun load(path: String): Single<Unit> {
        val sound = byPath(path)
        return if (sound != null) alreadyExist(sound) else {
            val loadId = soundPool.load(path, 1)
            loadNew(Sound(path = path, loadId = loadId))
        }
    }

    override fun load(afd: AssetFileDescriptor): Single<Unit> {
        val sound = byAssetFd(afd)
        return if (sound != null) alreadyExist(sound) else {
            val loadId = soundPool.load(afd, 1)
            loadNew(Sound(afd = afd, loadId = loadId))
        }
    }

    override fun load(fd: FileDescriptor, offset: Long, length: Long): Single<Unit> {
        val id = getFdId(fd, offset, length)
        val sound = byFd(id)
        return if (sound != null) alreadyExist(sound) else {
            val loadId = soundPool.load(fd, offset, length, 1)
            loadNew(Sound(fd = fd, fdId = id, loadId = loadId))
        }
    }

    private fun alreadyExist(sound: Sound) = if (sound.isLoaded) Single.just(Unit) else {
        val subject = subjects[sound.loadId]
        if (subject != null && !subject.hasComplete()) subject.first(Unit) else
            throw LoadSoundFail(sound.rawId ?: sound.path ?: sound.afd ?: sound.fd)
    }

    private fun loadNew(sound: Sound): Single<Unit> {
        val subject = AsyncSubject.create<Unit>()
        subjects[sound.loadId] = subject
        sounds[sound.loadId] = sound
        return subject.first(Unit)
    }

    override fun playBuilder(resId: Int): PlayBuilder = PlayBuilderImpl(resId)

    override fun playBuilder(path: String): PlayBuilder = PlayBuilderImpl(path)

    override fun playBuilder(afd: AssetFileDescriptor): PlayBuilder = PlayBuilderImpl(afd)

    override fun playBuilder(fd: FileDescriptor, offset: Long, length: Long): PlayBuilder {
        return PlayBuilderImpl(fd, offset, length)
    }

    override fun stop(resId: Int) {
        byResId(resId)?.let { stop(it) }
    }

    override fun stop(path: String) {
        byPath(path)?.let { stop(it) }
    }

    override fun stop(afd: AssetFileDescriptor) {
        byAssetFd(afd)?.let { stop(it) }
    }

    override fun stop(fd: FileDescriptor, offset: Long, length: Long) {
        byFd(getFdId(fd, offset, length))?.let { stop(it) }
    }

    override fun stopAll() {
        sounds.values.forEach { stop(it) }
    }

    private fun stop(sound: Sound) {
        sound.let { if (it.streamId != -1) soundPool.stop(it.streamId) }
    }

    override fun pause(resId: Int) {
        byResId(resId)?.let { pause(it) }
    }

    override fun pause(path: String) {
        byPath(path)?.let { pause(it) }
    }

    override fun pause(afd: AssetFileDescriptor) {
        byAssetFd(afd)?.let { pause(it) }
    }

    override fun pause(fd: FileDescriptor, offset: Long, length: Long) {
        byFd(getFdId(fd, offset, length))?.let { pause(it) }
    }

    override fun pauseAll() = soundPool.autoPause()

    private fun pause(sound: Sound) {
        sound.let { if (it.streamId != -1) soundPool.pause(it.streamId) }
    }

    override fun resume(resId: Int) {
        byResId(resId)?.let { resume(it) }
    }

    override fun resume(path: String) {
        byPath(path)?.let { resume(it) }
    }

    override fun resume(afd: AssetFileDescriptor) {
        byAssetFd(afd)?.let { resume(it) }
    }

    override fun resume(fd: FileDescriptor, offset: Long, length: Long) {
        byFd(getFdId(fd, offset, length))?.let { resume(it) }
    }

    override fun resumeAll() = soundPool.autoResume()

    private fun resume(sound: Sound) {
        sound.let { if (it.streamId != -1) soundPool.resume(it.streamId) }
    }

    override fun release() {
        soundPool.release()
        sounds.clear()
        subjects.values.forEach { if (!it.hasComplete()) it.onComplete() }
        subjects.clear()
    }

    private fun getFdId(descriptor: FileDescriptor, offset: Long, length: Long): Long {
        return 2 * descriptor.hashCode() + 3 * offset + 5 * length
    }

    private fun byResId(resId: Int) = sounds.values.find { it.rawId == resId }

    private fun byPath(path: String) = sounds.values.find { it.path == path }

    private fun byAssetFd(afd: AssetFileDescriptor) = sounds.values.find { it.afd == afd }

    private fun byFd(fdId: Long) = sounds.values.find { it.fdId == fdId }

    private inner class PlayBuilderImpl private constructor() : PlayBuilder {

        private var rawId: Int? = null
        private var path: String? = null
        private var afd: AssetFileDescriptor? = null
        private var fd: FileDescriptor? = null
        private var fdId: Long? = null

        private var leftVolume: Float = 1f
        private var rightVolume: Float = 1f
        private var priority: Int = 0
        private var loop: Int = 0
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
            fdId = getFdId(fd, offset, length)
        }

        override fun volume(leftVolume: Float, rightVolume: Float): PlayBuilder {
            return apply { this.leftVolume = leftVolume; this.rightVolume = rightVolume }
        }

        override fun leftVolume(volume: Float) = apply { leftVolume = volume }

        override fun rightVolume(volume: Float) = apply { rightVolume = volume }

        fun priority(priority: Int) = apply { this.priority = priority }

        override fun repeat(count: Int) = apply { loop = count }

        override fun looping() = apply { loop = -1 }

        override fun rate(rate: Float) = apply { this.rate = rate }

        override fun play() {
            val sound = rawId?.let { byResId(it) }
                ?: path?.let { byPath(it) }
                ?: afd?.let { byAssetFd(it) }
                ?: fdId?.let { byFd(it) }
                ?: throw UnknownSound(rawId ?: path ?: afd ?: fd)
            if (!sound.isLoaded) throw SoundNotLoaded(rawId ?: path ?: afd ?: fd)

            soundPool.play(sound.loadId, leftVolume, rightVolume, priority, loop, rate).also {
                sounds[sound.loadId] = sound.copy(streamId = it)
            }
        }
    }
}