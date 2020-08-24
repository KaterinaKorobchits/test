package my.luckydog.common.extensions

import android.graphics.PointF
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt

infix fun PointF.distance(point: PointF): Float {
    return sqrt((x - point.x).toDouble().pow(2) + (y - point.y).toDouble().pow(2)).toFloat()
}

infix fun PointF.angle(point: PointF): Float {
    return Math.toDegrees(atan(((y - point.y) / (x - point.x)).toDouble())).toFloat()
}