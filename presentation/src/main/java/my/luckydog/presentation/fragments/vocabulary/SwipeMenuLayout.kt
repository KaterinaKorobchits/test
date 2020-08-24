package my.luckydog.presentation.fragments.vocabulary

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import my.luckydog.presentation.R
import kotlin.math.abs

class SwipeMenuLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : ConstraintLayout(context, attrs, style) {

    companion object {
        var isSwiping = false

        private const val MIN_DISTANCE_TO_SWIPE = 30

        private const val NUMBER_OF_REQUIRED_CHILDREN = 3
        private const val INCORRECT_NUMBER_OF_CHILDREN_MESSAGE =
            "Incorrect number of children, required three"

        private const val INCORRECT_MAIN_CONTAINER_ID_MESSAGE =
            "Incorrect ID, main container (SwipeMenuLayout) should have ID"
        private const val INCORRECT_LEFT_CONTAINER_ID_MESSAGE =
            "Incorrect ID, left container should have correct ID"
        private const val INCORRECT_RIGHT_CONTAINER_ID_MESSAGE =
            "Incorrect ID, right container should have correct ID"
        private const val INCORRECT_FOREGROUND_CONTAINER_ID_MESSAGE =
            "Incorrect ID, foreground container should have correct ID"

    }

    private lateinit var startBackgroundSwipe: View
    private lateinit var leftBackgroundSwipe: View
    private lateinit var foregroundContainer: View

    private var onClickListener: OnClickListener? = null
    private var onSwipeListener: OnSwipeListener? = null

    private var positionOnActionDown = 0.0f
    private var distanceAfterMove = 0.0f
    private var translationByX = 0f

    private var leftContainerWidth = 0
    private var rightContainerWidth = 0
    private var halfLeftContainerWidth = 0
    private var halfRightContainerWidth = 0

    private var leftExpanded = false
    private var rightExpanded = false

    private var needCalculateWidth = true

    override fun onFinishInflate() {
        println("onFinishInflate")
        super.onFinishInflate()
        if (childCount != NUMBER_OF_REQUIRED_CHILDREN) throw Throwable(
            INCORRECT_NUMBER_OF_CHILDREN_MESSAGE
        )
        foregroundContainer = findViewById(R.id.foregroundContainer) ?: throw Throwable(
            INCORRECT_FOREGROUND_CONTAINER_ID_MESSAGE
        )
        startBackgroundSwipe = findViewById(R.id.startContainer) ?: throw Throwable(
            INCORRECT_LEFT_CONTAINER_ID_MESSAGE
        )
        leftBackgroundSwipe = findViewById(R.id.endContainer) ?: throw Throwable(
            INCORRECT_RIGHT_CONTAINER_ID_MESSAGE
        )

        if (id == View.NO_ID) throw Throwable(INCORRECT_MAIN_CONTAINER_ID_MESSAGE)

        //enableBackgroundContainerClickEventsOnExpand()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        println("onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (needCalculateWidth) {
            needCalculateWidth = false
            calculateWidth()
        }
    }

    private fun calculateWidth() {
        leftContainerWidth = startBackgroundSwipe.measuredWidth
        halfLeftContainerWidth = leftContainerWidth / 2
        rightContainerWidth = leftBackgroundSwipe.measuredWidth
        halfRightContainerWidth = rightContainerWidth / 2
    }

    fun apply(leftExpanded: Boolean, rightExpanded: Boolean) {
        this.leftExpanded = leftExpanded
        this.rightExpanded = rightExpanded

        post {
            calculateWidth()

            if (leftExpanded || rightExpanded) {
                translationByX =
                    (if (leftExpanded) leftContainerWidth else -rightContainerWidth).toFloat()
                refreshForegroundPosition(translationByX)
                //enableBackgroundContainerClickEventsOnExpand()
            } else collapse()
        }
    }

    private fun enableBackgroundContainerClickEventsOnExpand() {
        if (!leftExpanded && !rightExpanded) {
            (startBackgroundSwipe as ViewGroup).let { viewGroup ->
                viewGroup.children.forEach {
                    it.isClickable = false
                }
            }
            (leftBackgroundSwipe as ViewGroup).let { viewGroup ->
                viewGroup.children.forEach {
                    it.isClickable = false
                }
            }
        } else {
            if (leftExpanded) {
                (startBackgroundSwipe as ViewGroup).let { viewGroup ->
                    viewGroup.children.forEach {
                        it.isClickable = true
                    }
                }
                (leftBackgroundSwipe as ViewGroup).let { viewGroup ->
                    viewGroup.children.forEach {
                        it.isClickable = false
                    }
                }
            } else {
                (startBackgroundSwipe as ViewGroup).let { viewGroup ->
                    viewGroup.children.forEach {
                        it.isClickable = false
                    }
                }
                (leftBackgroundSwipe as ViewGroup).let { viewGroup ->
                    viewGroup.children.forEach {
                        it.isClickable = true
                    }
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        println("onTouchEvent ${event.action} ${event.x}}")

        val gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    println("onSingleTapUp performc lick")
                    performClick()
                    return false
                }
            })
        gestureDetector.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> positionOnActionDown = event.x
            MotionEvent.ACTION_UP -> onReleasePress(false)
            MotionEvent.ACTION_CANCEL -> onReleasePress(true)
            MotionEvent.ACTION_MOVE -> onMove(event.x)
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun onReleasePress(loseTouch: Boolean) {
        println("onReleasePress $loseTouch")
        val wasSwiping = checkIfSwiping()

        if (wasSwiping) {
            if (translationByX > halfLeftContainerWidth ||
                abs(translationByX) > halfRightContainerWidth
            )
                expand() else collapse()
        }

        if (wasSwiping) onSwipeListener?.onSwipe(leftExpanded, rightExpanded)

        if (!loseTouch && !wasSwiping) onClickListener?.onClick(this)
    }

    private fun expand() {
        println("expand")
        if (distanceAfterMove > 0) leftExpanded = true else rightExpanded = true
        translationByX =
            (if (distanceAfterMove > 0) leftContainerWidth else -rightContainerWidth).toFloat()
        refreshForegroundPosition(translationByX)
        enableBackgroundContainerClickEventsOnExpand()
        clearDistanceAfterMove()
    }

    private fun collapse() {
        println("collapse")
        leftExpanded = false
        rightExpanded = false
        translationByX = 0f
        refreshForegroundPosition(translationByX)
        enableBackgroundContainerClickEventsOnExpand()
        clearDistanceAfterMove()
    }

    private fun clearDistanceAfterMove() {
        distanceAfterMove = 0f
    }

    private fun onMove(position: Float) {
        println("onMove")
        if (leftExpanded || rightExpanded) calculateTranslationCollapsing(position)
        else calculateTranslationExpanding(position)
        if (checkIfSwiping()) refreshForegroundPosition(translationByX)
    }

    private fun calculateTranslationExpanding(position: Float) {
        println("calculateMarginOnExpanding ")
        distanceAfterMove = position - positionOnActionDown
        translationByX = when {
            distanceAfterMove > 0 && distanceAfterMove > leftContainerWidth -> leftContainerWidth.toFloat()
            distanceAfterMove < 0 && abs(distanceAfterMove) > rightContainerWidth -> -rightContainerWidth.toFloat()
            else -> distanceAfterMove
        }
        println("calculateMarginOnExpanding - $distanceAfterMove --- position = $position calculatedNewMargin = $translationByX")
    }

    private fun calculateTranslationCollapsing(position: Float) {
        distanceAfterMove = positionOnActionDown - position
        println("calculateMarginOnCollapsing $distanceAfterMove")
        translationByX = when {
            distanceAfterMove > 0 && distanceAfterMove > leftContainerWidth -> 0f
            distanceAfterMove > 0 -> (leftContainerWidth - distanceAfterMove)
            distanceAfterMove < 0 && abs(distanceAfterMove) > rightContainerWidth -> 0f
            distanceAfterMove < 0 -> -(rightContainerWidth - abs(distanceAfterMove))
            else -> distanceAfterMove
        }
    }

    private fun refreshForegroundPosition(translationX: Float) {
        println("refreshForegroundPosition - $translationX")
        foregroundContainer.translationX = translationX
        foregroundContainer.requestLayout()
    }

    private fun checkIfSwiping(): Boolean = abs(distanceAfterMove) > MIN_DISTANCE_TO_SWIPE.also {
        println("isSwiping = $isSwiping")
    }

    fun setOnSwipeListener(onSwipeListener: OnSwipeListener) {
        this.onSwipeListener = onSwipeListener
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        this.onClickListener = onClickListener
    }

    interface OnSwipeListener {
        fun onSwipe(leftExpanded: Boolean, rightExpanded: Boolean)
    }
}