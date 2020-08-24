package my.luckydog.presentation.fragments.vocabulary

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class SwipeMenuLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun canScrollVertically() = !SwipeMenuLayout.isSwiping
}