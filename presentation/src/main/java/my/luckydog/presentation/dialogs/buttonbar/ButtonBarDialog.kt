package my.luckydog.presentation.dialogs.buttonbar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.ObservableField
import my.luckydog.presentation.databinding.DialogButtonBinding
import my.luckydog.presentation.extensions.inflater

class ButtonBarDialog : LinearLayout {

    private val form = ButtonDialogForm()
    private lateinit var handler: ButtonDialogHandler

    private val positiveCallBack = object : ButtonHandler {
        override fun onClick() = handler.positive.invoke()
    }

    private val negativeCallBack = object : ButtonHandler {
        override fun onClick() = handler.negative.invoke()
    }

    private val neutralCallBack = object : ButtonHandler {
        override fun onClick() = handler.neutral.invoke()
    }

    private val childList = mutableListOf<TextView>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        addView(createButton(form.neutral, neutralCallBack))
        addView(createButton(form.negative, negativeCallBack))
        addView(createButton(form.positive, positiveCallBack))
    }

    fun form(form: ButtonDialogForm) {
        this.form.negative.set(form.negative.get())
        this.form.positive.set(form.positive.get())
        this.form.neutral.set(form.neutral.get())
    }

    fun handler(handler: ButtonDialogHandler) {
        this.handler = handler
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) childList.add(getChildAt(i) as TextView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val listLines = mutableListOf<Int>()
        childList.forEach { listLines.add(it.lineCount) }
        if (listLines.find { it > 1 } != null) setStacked(true) else setStacked(false)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun createButton(text: ObservableField<String>, handler: ButtonHandler): View {
        return DialogButtonBinding.inflate(context.inflater(), this, false).apply {
            this.text = text
            this.handler = handler
        }.root
    }

    private fun setStacked(stacked: Boolean) {
        orientation = if (stacked) VERTICAL else HORIZONTAL
        gravity = if (stacked) Gravity.END else Gravity.BOTTOM

        if (stacked) {
            childList.forEach { it.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT) }
        } else {
            for (i in 0 until childList.size) {
                val params = LayoutParams(0, WRAP_CONTENT, if (i == 0) 1.5f else 1f).apply {
                    setMargins(8, 0, 8, 0)
                }
                childList[i].layoutParams = params
            }
        }
    }
}
