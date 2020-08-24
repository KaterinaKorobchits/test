package my.luckydog.interactors.about

data class ItemChip(
    val text: String,
    val icon: Int? = null,
    val closable: Boolean = false,
    val checkable: Boolean = true,
    val isChecked: Boolean = false
)