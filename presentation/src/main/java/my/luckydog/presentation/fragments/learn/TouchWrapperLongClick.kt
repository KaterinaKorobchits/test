package my.luckydog.presentation.fragments.learn

import android.content.ClipData
import android.os.Build
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class TouchWrapperLongClick : TouchWrapper() {

    override fun actionMove(event: MotionEvent, v: View) {
        if (isLongClick(event)) {
            println("performLongClick")
            v.performLongClick()
        } else if (isMove(event)) {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(v)
            println("LongClick - startDragAndDrop $v")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(data, shadowBuilder, v, 0)
            } else v.startDrag(data, shadowBuilder, v, 0)

            v.visibility = View.INVISIBLE
        }
    }

    private fun isLongClick(event: MotionEvent): Boolean = with(event) {
        eventTime - downTime > 300 && abs(x - startX) < 10 && abs(y - startY) < 10
    }
}