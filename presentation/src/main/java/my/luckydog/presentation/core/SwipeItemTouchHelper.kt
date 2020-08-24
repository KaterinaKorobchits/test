package my.luckydog.presentation.core

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.dpFromPx

class SwipeItemTouchHelper(
    private val context: Context,
    private val swipeFlags: Int = ItemTouchHelper.START or ItemTouchHelper.END,
    private val onSwipe: (position: Int) -> Unit
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int = makeMovementFlags(0, swipeFlags)

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //onSwipe(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            var icon = ContextCompat.getDrawable(context!!, R.drawable.ic_info_black_24dp)
            var iconLeft = 0
            var iconRight = 0

            val background: ColorDrawable
            val itemView = viewHolder.itemView
            val margin = context.dpFromPx(32f)
            val iconWidth = icon!!.intrinsicWidth
            val iconHeight = icon.intrinsicHeight
            val cellHeight = itemView.bottom - itemView.top
            val iconTop = itemView.top + (cellHeight - iconHeight) / 2
            val iconBottom = iconTop + iconHeight

            // Right swipe.
            if (dX > 0) {
                icon = ContextCompat.getDrawable(context!!, R.drawable.ic_email_black_24dp)
                background = ColorDrawable(Color.RED)
                background.setBounds(0, itemView.getTop(), (itemView.getLeft() + dX/5).toInt(), itemView.getBottom())
                iconLeft = margin
                iconRight = margin + iconWidth
            } /*Left swipe.*/ else {
                icon = ContextCompat.getDrawable(context!!, R.drawable.ic_home_black_24dp)
                background = ColorDrawable(Color.BLUE)
                background.setBounds((itemView.right - dX).toInt(), itemView.getTop(), 0, itemView.getBottom())
                iconLeft = itemView.right - margin - iconWidth
                iconRight = itemView.right - margin
            }
            background.draw(c)
            icon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            icon?.draw(c)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}