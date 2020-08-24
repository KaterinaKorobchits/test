package my.luckydog.presentation.core.sound

interface PlayBuilder {

    fun volume(leftVolume: Float, rightVolume: Float): PlayBuilder

    fun leftVolume(volume: Float): PlayBuilder

    fun rightVolume(volume: Float): PlayBuilder

    fun repeat(count: Int): PlayBuilder

    fun looping(): PlayBuilder

    fun rate(rate: Float): PlayBuilder

    @Throws(UnknownSound::class, SoundNotLoaded::class)
    fun play()
}