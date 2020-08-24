package my.luckydog.common.extensions

import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt

fun distance(p1X: Double, p1Y: Double, p2X: Double, p2Y: Double): Double {
    return sqrt((p1X - p2X).pow(2) + (p1Y - p2Y).pow(2))
}

fun distance(p1X: Float, p1Y: Float, p2X: Float, p2Y: Float): Float {
    return distance(p1X.toDouble(), p1Y.toDouble(), p2X.toDouble(), p2Y.toDouble()).toFloat()
}

fun distanceSign(startX: Double, startY: Double, endX: Double, endY: Double): Double {
    val distance = distance(startX, startY, endX, endY)
    return (if (endX - startX > 0) 1 else -1) * distance
}

fun distanceSign(startX: Float, startY: Float, endX: Float, endY: Float): Float {
    val distance = distance(startX, startY, endX, endY)
    return (if (endX - startX > 0) 1 else -1) * distance
}


fun angle(p1X: Double, p1Y: Double, p2X: Double, p2Y: Double): Double {
    return Math.toDegrees(atan((p1Y - p2Y) / (p1X - p2X)))
}

fun angle(p1X: Float, p1Y: Float, p2X: Float, p2Y: Float): Float {
    return angle(p1X.toDouble(), p1Y.toDouble(), p2X.toDouble(), p2Y.toDouble()).toFloat()
}