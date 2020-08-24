package my.luckydog.presentation.core

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import my.luckydog.interactors.about.ItemChip
import my.luckydog.presentation.dialogs.buttonbar.ButtonBarDialog
import my.luckydog.presentation.views.BaseImageView

@BindingAdapter("errorText")
fun TextInputLayout.setErrorMessage(errorMessage: String?) {
    error = errorMessage
}

@BindingAdapter("srcIcon", "drawableIcon", requireAll = false)
fun AppCompatImageView.setIcon(resource: Int?, drawable: Drawable?) {
    resource?.let { setImageResource(it) }
    drawable?.let { setImageDrawable(it) }
}

@BindingAdapter("positiveText", "positiveHandler", requireAll = true)
fun ButtonBarDialog.setPositive(positive: String?, handler: () -> Unit) {
    positive?.let { positive(it, handler) }
}

@BindingAdapter("negativeText", "negativeHandler", requireAll = true)
fun ButtonBarDialog.setNegative(negative: String?, handler: () -> Unit) {
    negative?.let { negative(it, handler) }
}

@BindingAdapter("neutralText", "neutralHandler", requireAll = true)
fun ButtonBarDialog.setNeutral(neutral: String?, handler: () -> Unit) {
    neutral?.let { neutral(it, handler) }
}

@BindingAdapter("lockDrawer")
fun DrawerLayout.setDrawerLockMode(lock: Boolean) {
    setDrawerLockMode(if (lock) LOCK_MODE_LOCKED_CLOSED else LOCK_MODE_UNLOCKED)
}

/*@BindingAdapter("items")
fun <T> RecyclerView.setData(data: List<T>) {
    if (adapter is BaseAdapter<*>) (adapter as BaseAdapter<T>).setItems(data)
}*/

@BindingAdapter("items", "adapter", requireAll = true)
fun <T> RecyclerView.setData(items: List<T>, adapter: BaseAdapter<T>) {
    if (this.adapter != adapter) this.adapter = adapter
    adapter.setItems(items)
}

@BindingAdapter("isLinearVertical")
fun RecyclerView.setLinearLayoutManager(isVertical: Boolean) {
    layoutManager = LinearLayoutManager(context).apply {
        orientation =
            if (isVertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL
    }
}

@BindingAdapter("gridLayoutColumns")
fun RecyclerView.setGridLayoutColumns(columns: Int) {
    layoutManager = GridLayoutManager(context, columns)
}

@BindingAdapter("adapter")
fun <T> RecyclerView.setRecyclerAdapter(adapter: BaseAdapter<T>) {
    this.adapter = adapter
}

@BindingAdapter("itemMargin")
fun RecyclerView.setRecyclerItemMargin(spaceDp: Int) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.set(spaceDp, spaceDp, spaceDp, spaceDp)
        }
    })
}

@BindingAdapter("isScrollBehavior")
fun View.setScrollBehavior(isScroll: Boolean) {
    val params = layoutParams
    if (params is CoordinatorLayout.LayoutParams) {
        params.behavior = if (isScroll) AppBarLayout.ScrollingViewBehavior() else null
    }
}

@BindingAdapter("url")
fun BaseImageView.setImageFromUrl(url: String) = load(url)

@BindingAdapter("chips")
fun ChipGroup.bindChips(chips: List<ItemChip>) {
    if (childCount > 0) removeAllViews()

    chips.forEachIndexed { index, _ ->
        val chip = Chip(context)
        chip.text = chips[index].text
        chip.tag = index

        chips[index].icon?.let { chip.chipIcon = ContextCompat.getDrawable(context, it) }
        chip.isCloseIconVisible = chips[index].closable
        chip.isCheckable = chips[index].checkable
        addView(chip)
    }
}

@BindingAdapter("changed")
fun ChipGroup.setChanges(handler: (index: Int) -> Unit) {

    val listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        handler(buttonView.tag as Int)
    }

    (0..childCount).forEach {
        val child = getChildAt(it)
        if (child is CompoundButton) child.setOnCheckedChangeListener(listener)
    }
}