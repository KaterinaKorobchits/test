package my.luckydog.presentation.dialogs.buttonbar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.inflater
import my.luckydog.presentation.core.extensions.setSafeOnClickListener

class ButtonBarDialog : LinearLayout {

    private var lastWidth: Int = 0
    private var lastHeight: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun positive(text: String, handler: () -> Unit) {
        setup(R.id.btn_positive, text, handler, -1)
    }

    fun negative(text: String, handler: () -> Unit) {
        setup(R.id.btn_negative, text, handler, if (childCount > 1) 1 else -1)
    }

    fun neutral(text: String, handler: () -> Unit) {
        setup(R.id.btn_neutral, text, handler, if (childCount > 0) 0 else -1)
    }

    private fun setup(id: Int, text: String, handler: () -> Unit, index: Int) {
        (findViewById(id) ?: inflate(id, handler).also { addView(it, index) }).let {
            it.text = text
            it.visibility = if (text.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun inflate(id: Int, handler: () -> Unit): TextView {
        return context.inflater().inflate(R.layout.dialog_button, this, false).also {
            it.id = id
            it.setSafeOnClickListener { handler.invoke() }
            (it.layoutParams as LayoutParams).weight = 1f
        } as TextView
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (lastWidth != width || lastHeight != height) sizeChanged()

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun sizeChanged() {
        val isNeedStacked = (0 until childCount).find {
            (getChildAt(it) as TextView).lineCount > 1
        } != null
        if (isNeedStacked != isStacked()) setStacked(isNeedStacked)
    }

    private fun setStacked(stacked: Boolean) {
        orientation = if (stacked) VERTICAL else HORIZONTAL
        gravity = if (stacked) Gravity.END else Gravity.BOTTOM
    }

    private fun isStacked() = orientation == VERTICAL
}
