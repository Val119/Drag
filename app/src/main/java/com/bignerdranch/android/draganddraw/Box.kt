package com.bignerdranch.android.draganddraw

import android.graphics.PointF

data class Box(val start: PointF) {

    // The end point of the box, initialized to the start point by default
    var end: PointF = start

    // The left coordinate of the box (minimum of start.x and end.x)
    val left: Float
        get() = Math.min(start.x, end.x)

    // The right coordinate of the box (maximum of start.x and end.x)
    val right: Float
        get() = Math.max(start.x, end.x)

    // The top coordinate of the box (minimum of start.y and end.y)
    val top: Float
        get() = Math.min(start.y, end.y)

    // The bottom coordinate of the box (maximum of start.y and end.y)
    val bottom: Float
        get() = Math.max(start.y, end.y)

    // The width of the box (absolute difference between end.x and start.x)
    val width: Float
        get() = Math.abs(end.x - start.x)

    // The height of the box (absolute difference between end.y and start.y)
    val height: Float
        get() = Math.abs(end.y - start.y)
}
