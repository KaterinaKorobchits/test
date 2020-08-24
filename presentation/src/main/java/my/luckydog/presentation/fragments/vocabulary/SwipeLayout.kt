package my.luckydog.presentation.fragments.vocabulary

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import my.luckydog.common.extensions.distance
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.pxFromDp
import kotlin.math.abs

class SwipeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : ConstraintLayout(context, attrs, style) {

    private companion object {
        private const val VELOCITY_THRESHOLD = 1200f
    }

    private lateinit var foreground: View

    private val pxPerMs = context.pxFromDp(300f) / 1000
    private var lastWidth: Int = 0
    private var lastHeight: Int = 0

    private val interpolator = LinearInterpolator()
    private var animator: ValueAnimator? = null

    private var current: SwipeState = SwipeState.NONE

    private var startSwipe = 0f
    private var endSwipe = 0f

    private var swipeListener: SwipeListener? = null

    private val enableSwipe: Boolean = true
    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        // @see https://code.google.com/p/android/issues/detail?id=8233
        override fun onDown(e: MotionEvent): Boolean = true

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            fling(e1, e2, velocityX)
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            performClick()
            return true
        }
    }
    private val detector = GestureDetector(context, gestureListener)

    private val foregroundTouch = OnTouchListener { view, event ->
        val action = event.action
        when {
            !enableSwipe && action == MotionEvent.ACTION_UP -> {
                view.performClick()
                true
            }
            !enableSwipe -> true
            detector.onTouchEvent(event) -> true
            action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL -> {
                foregroundUp()
                true
            }
            action == MotionEvent.ACTION_MOVE -> {
                foregroundMove(event)
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    override fun onFinishInflate() {
        foreground = findViewById(R.id.foregroundContainer)
        foreground.setOnTouchListener(foregroundTouch)

        super.onFinishInflate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        if (lastWidth != width || lastHeight != height) {
            val params = foreground.layoutParams as MarginLayoutParams

            val endContainer = findViewById<View>(R.id.endContainer)
            val startContainer = findViewById<View>(R.id.startContainer)

            startSwipe = (startContainer.measuredWidth - params.marginStart).toFloat()
            endSwipe = (endContainer.measuredWidth - params.marginEnd).toFloat()
        }
    }

    private fun fling(e1: MotionEvent, e2: MotionEvent, velocityX: Float) {
        if (abs(velocityX) < VELOCITY_THRESHOLD) foregroundUp() else {
            val distance = distance(e1.x, e1.y, e2.x, e2.y)
            val targetX = distance * velocityX / VELOCITY_THRESHOLD + foreground.translationX
            val translationX = roundUp(targetX)
            performUp(translationX)
        }
    }

    private fun foregroundUp() {
        val translationX = roundUp(foreground.translationX)
        performUp(translationX)
    }

    private fun roundUp(targetX: Float): Float = when {
        targetX > startSwipe / 2 -> startSwipe
        targetX < 0 && abs(targetX) > endSwipe / 2 -> -endSwipe
        else -> 0f
    }

    private fun performUp(translationX: Float) {
        val duration = duration(foreground.translationX, translationX)
        foregroundMove(translationX, duration)

        val newState = state(translationX)
        current = newState
        swipeListener?.onSwipe(newState)
    }

    private fun state(translationX: Float) = when (translationX) {
        -endSwipe -> SwipeState.END
        startSwipe -> SwipeState.START
        else -> SwipeState.NONE
    }

    private fun foregroundMove(event: MotionEvent) {
        val targetX = foreground.translationX + distanceHistory(event)
        val translationX = round(targetX)
        foregroundMove(translationX, 0L)
    }

    private fun distanceHistory(event: MotionEvent): Float = if (event.historySize > 1) {
        val startX = event.getHistoricalX(event.historySize - 1)
        val endX = event.getHistoricalX(0)
        startX - endX
    } else 0f

    private fun round(targetX: Float): Float = when {
        targetX > startSwipe -> startSwipe
        targetX < 0 && abs(targetX) > endSwipe -> -endSwipe
        else -> targetX
    }

    private fun foregroundMove(translationX: Float, duration: Long) {
        if (duration == 0L) changeTranslationX(translationX) else {
            animator?.cancel()
            animator = prepareAnimator(foreground.translationX, translationX, duration)
            animator?.start()
        }
    }

    private fun prepareAnimator(startX: Float, endX: Float, duration: Long): ValueAnimator {
        return ValueAnimator.ofFloat(startX, endX).also {
            it.interpolator = interpolator
            it.duration = duration
            it.addUpdateListener { animation -> changeTranslationX(animation.animatedValue as Float) }
        }
    }

    private fun duration(currentX: Float, targetX: Float): Long {
        return (abs(currentX - targetX) / pxPerMs).toLong()
    }

    fun applySwipe(state: SwipeState) {
        changeState(state)
        val translationX = when (state) {
            SwipeState.START -> startSwipe
            SwipeState.END -> -endSwipe
            SwipeState.NONE -> 0f
        }
        changeTranslationX(translationX)
    }

    private fun changeTranslationX(translationX: Float) {
        foreground.translationX = translationX
    }

    private fun changeState(state: SwipeState) {
        current = state
    }

    fun setSwipeListener(swipeListener: SwipeListener) {
        this.swipeListener = swipeListener
    }

    interface SwipeListener {
        fun onSwipe(state: SwipeState)
    }
}