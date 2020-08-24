package my.luckydog.boundaries.core.dictionary.entities

sealed class DicEntry {

    data class Success(
        val text: String,
        val pos: String?,
        val transcription: String,
        val translations: List<Translation>
    ) : DicEntry()

    object Fail : DicEntry()
}