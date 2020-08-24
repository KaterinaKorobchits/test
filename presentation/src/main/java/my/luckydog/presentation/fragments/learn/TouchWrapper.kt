package my.luckydog.presentation.fragments.learn

import android.content.ClipData
import android.os.Build
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

open class TouchWrapper : View.OnTouchListener {

    protected var startX: Float = 0.0f
    protected var startY: Float = 0.0f

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                println("ACTION_DOWN")
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                println("ACTION_MOVE")
                actionMove(event, v)
                /*if (isMove(event)) {
                    val data = ClipData.newPlainText("", "")
                    val shadowBuilder = View.DragShadowBuilder(v)
                    println("startDragAndDrop $v")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        v.startDragAndDrop(data, shadowBuilder, v, 0)
                    } else v.startDrag(data, shadowBuilder, v, 0)

                    v.visibility = View.INVISIBLE
                }*/
            }
            MotionEvent.ACTION_UP -> {
                println("ACTION_UP")
                if (isClick(event)) {
                    v.visibility = View.VISIBLE
                    println("click")
                    v.performClick()
                }
            }
        }
        return true
    }

    open fun actionMove(event: MotionEvent, v: View){
        if (isMove(event)) {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(v)
            println("startDragAndDrop $v")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(data, shadowBuilder, v, 0)
            } else v.startDrag(data, shadowBuilder, v, 0)

            v.visibility = View.INVISIBLE
        }
    }

    private fun isClick(event: MotionEvent): Boolean = with(event) {
        eventTime - downTime < 150 && abs(x - startX) < 10 && abs(y - startY) < 10
    }

    protected fun isMove(event: MotionEvent): Boolean = with(event) {
        abs(x - startX) > 10 || abs(y - startY) > 10
    }
}