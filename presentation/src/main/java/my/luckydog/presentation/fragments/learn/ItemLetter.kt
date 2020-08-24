package my.luckydog.presentation.fragments.learn

open class ItemLetter(
    val id: Long,
    val letter: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ItemLetter
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}