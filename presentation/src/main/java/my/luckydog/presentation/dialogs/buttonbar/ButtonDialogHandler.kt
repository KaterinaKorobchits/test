package my.luckydog.presentation.dialogs.buttonbar

data class ButtonDialogHandler (
    val positive: () -> Unit = {},
    val negative: () -> Unit = {},
    val neutral: () -> Unit = {}
)