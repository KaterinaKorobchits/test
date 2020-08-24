package my.luckydog.presentation.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat.getColor
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.getBitmap
import my.luckydog.presentation.core.extensions.rotate
import my.luckydog.presentation.core.extensions.typedArray
import kotlin.LazyThreadSafetyMode.NONE

class PawsView : View {

    companion object {
        private const val MIN_COUNT_PAW = 6
        private const val MAX_PAW_SIZE_DP = 24
        private const val PAW_SIZE_PERCENT = 0.5f
        private const val ITERATION_DURATION = 200
        private const val NUMBER_ACTIVE_PAW = 4
        private const val NUMBER_RECENTLY_ACTIVE_PAW = 2
        private const val ALPHA_ACTIVE_PAW = 255
        private const val ALPHA_DEFAULT_PAW = 0
        private const val ALPHA_RECENTLY_ACTIVE_PAW = 130
        private const val ALPHA_NO_ACTIVE_PAW = 50
    }

    private val maxPawSize: Float by lazy(NONE) { MAX_PAW_SIZE_DP * resources.displayMetrics.density }
    private val defPawColor: Int by lazy(NONE) { getColor(context, R.color.light_colorPrimary) }
    private val paint: Paint by lazy(NONE) {
        Paint(Paint.ANTI_ALIAS_FLAG).apply { setColor(this, defPawColor) }
    }

    private lateinit var bitmap: Bitmap

    private val linearInterpolator = LinearInterpolator()
    private var animator: ValueAnimator? = null
    private var lastWidth: Int = 0
    private var lastHeight: Int = 0
    private var isHorizontal: Boolean = true
    private var listPawsCoord = emptyList<PointF>()
    private var iteration: Int = Int.MIN_VALUE

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let { getAttrs(it) }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        if (lastWidth != width || lastHeight != height) sizeChanged(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        if (lastHeight != 0 || lastWidth != 0)
            for (i in listPawsCoord.indices) {
                drawPaw(canvas, listPawsCoord[i].x, listPawsCoord[i].y, calculateAlpha(i))
            }
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        super.onDetachedFromWindow()
    }

    private fun getAttrs(attrs: AttributeSet) {
        context.typedArray(attrs, R.styleable.PawsView).apply {
            val pawColor = getColor(R.styleable.PawsView_pawColor, defPawColor)
            setColor(paint, pawColor)
            recycle()
        }
    }

    private fun sizeChanged(width: Int, height: Int) {
        lastWidth = width
        lastHeight = height
        isHorizontal = width > height

        var pawSize = minOf(minOf(width, height) * PAW_SIZE_PERCENT, maxPawSize)
        var countPaws = (maxOf(width, height) / pawSize).toInt()
        if (countPaws < MIN_COUNT_PAW) {
            countPaws = MIN_COUNT_PAW
            pawSize = maxOf(width, height) / MIN_COUNT_PAW.toFloat()
        }
        listPawsCoord = calculateCoordinates(width, height, countPaws, pawSize)

        bitmap = context.getBitmap(R.drawable.ic_pets_black_24dp, pawSize.toInt())
        if (isHorizontal) bitmap = bitmap.rotate(90F)

        animator?.cancel()
        animator = prepareAnimator(listPawsCoord.size + 4).apply { start() }
    }

    private fun calculateCoordinates(
        width: Int,
        height: Int,
        countPaws: Int,
        pawSize: Float
    ): List<PointF> {
        val coordinates = ArrayList<PointF>()
        val length = maxOf(width, height)
        val margin = (length % pawSize) / 2f
        for (i in 0 until countPaws) {
            val x = margin + pawSize * i
            val y = if ((i % 2) == 0) 0f else pawSize
            val point = if (isHorizontal) PointF(x, y) else PointF(y, length - x - pawSize)
            coordinates.add(point)
        }
        return coordinates
    }

    private fun prepareAnimator(end: Int): ValueAnimator {
        return ValueAnimator.ofInt(0, end).apply {
            repeatCount = ValueAnimator.INFINITE
            interpolator = linearInterpolator
            duration = end.toLong() * ITERATION_DURATION
            addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                if (value != iteration) {
                    iteration = value
                    invalidate()
                }
            }
        }
    }

    private fun setColor(paint: Paint, color: Int) {
        paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    private fun drawPaw(canvas: Canvas, left: Float, top: Float, alpha: Int) {
        paint.alpha = alpha
        canvas.drawBitmap(bitmap, left, top, paint)
    }

    private fun calculateAlpha(numberPaw: Int): Int = when {
        numberPaw > iteration -> ALPHA_DEFAULT_PAW
        numberPaw > iteration - NUMBER_ACTIVE_PAW -> ALPHA_ACTIVE_PAW
        numberPaw > iteration - NUMBER_ACTIVE_PAW - NUMBER_RECENTLY_ACTIVE_PAW -> ALPHA_RECENTLY_ACTIVE_PAW
        else -> ALPHA_NO_ACTIVE_PAW
    }
}

