package my.luckydog.presentation.core

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract val data: MutableList<T>

    override fun getItemCount(): Int = data.size

    fun getItem(position: Int): T = data.elementAt(position)

    fun setItems(items: List<T>) {
        val callback = diffCallback(data, items)
        val diff = DiffUtil.calculateDiff(callback)
        data.run {
            clear()
            addAll(items)
        }

        diff.dispatchUpdatesTo(this)
    }

    abstract fun diffCallback(old: List<T>, new: List<T>): DiffUtil.Callback
}