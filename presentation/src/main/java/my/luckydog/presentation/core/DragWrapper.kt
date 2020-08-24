package my.luckydog.presentation.core

import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DragWrapper<T>(
    private val recycler: RecyclerView,
    private val dropAction: (item: T) -> Unit
) : View.OnDragListener {

    override fun onDrag(v: View, event: DragEvent): Boolean {
        println("onDrag ${event.localState}----$event")
        //event.localState?:return true
        val draggedView = (event.localState as View)

        when (event.action) {
            DragEvent.ACTION_DROP -> {
                val a =
                    recycler.adapter?.getItemViewType(recycler.getChildAdapterPosition(draggedView))
                println("ACTION_DROP $a ${recycler.getChildAdapterPosition(draggedView)}")
                val draggedPosition = recycler.getChildAdapterPosition(draggedView)
                if (draggedPosition != -1 && recycler.adapter is BaseAdapter<*>) {
                    val position = recycler.getChildAdapterPosition(draggedView)
                    dropAction((recycler.adapter as BaseAdapter<T>).getItem(position))
                } else return false
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                println("ACTION_DRAG_ENDED ${event.result}")
                if (!event.result) draggedView.visibility = View.VISIBLE
            }
        }
        return true
    }
}