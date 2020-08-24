package my.luckydog.presentation.fragments.vocabulary

data class SampleData(
    val title: String,
    val description: String,
    var swipeState: SwipeState = SwipeState.NONE
)