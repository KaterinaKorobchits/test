package my.luckydog.presentation.fragments.vocabulary

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import my.luckydog.common.extensions.angle
import my.luckydog.common.extensions.distance
import my.luckydog.presentation.core.extensions.pxFromDp
import kotlin.math.abs

class SwipeRecycler @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    private companion object {
        private const val SWIPE_ANGLE = 30f
    }

    private val distance = context.pxFromDp(3f)

    private val down = PointF(-1f, -1f)
    private val move = PointF(-1f, -1f)

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_UP) performClick()
        return super.onTouchEvent(e)
    }

    override fun performClick() = super.performClick()

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        return when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                down.x = e.x
                down.y = e.y
                super.onInterceptTouchEvent(e)
            }
            MotionEvent.ACTION_MOVE -> {
                move.x = e.x
                move.y = e.y
                val child = findChildViewUnder(e.x, e.y)
                if (child is SwipeLayout) {
                    distance < down.distance(move) && SWIPE_ANGLE < abs(down.angle(move))
                } else super.onInterceptTouchEvent(e)
            }
            else -> super.onInterceptTouchEvent(e)
        }
    }
}