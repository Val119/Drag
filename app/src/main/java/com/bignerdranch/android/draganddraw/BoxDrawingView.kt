package com.bignerdranch.android.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

private const val TAG = "BoxDrawingView"

class BoxDrawingView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    // Current box being drawn
    private var currentBox: Box? = null

    // List to store all the drawn boxes
    private val boxes = mutableListOf<Box>()

    // Paint for drawing the box outline
    private val boxPaint = Paint().apply {
        color = 0x22ff0000.toInt()
    }

    // Paint for filling the background
    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }

    // Number of shapes drawn so far (Part 4)
    private var numShapes: Int = 0

    // Flag to indicate whether a shape is currently being drawn (Part 3)
    private var drawing: Boolean = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                // Reset drawing state
                // Part 4: Limit the number of shapes drawn to 3
                if (numShapes >= 3) {
                    currentBox = null
                } else {
                    // Create a new box and add it to the list when touch is initiated
                    currentBox = Box(current).also {
                        boxes.add(it)
                    }
                    // Part 3: Set drawing flag to true
                    drawing = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                // Update the current box while moving the touch point
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                // Finalize the current box when touch is released
                updateCurrentBox(current)
                // Part 3: Reset drawing state
                currentBox = null
                drawing = false
                numShapes += 1
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                // Cancel the current box drawing if needed
                currentBox = null
            }
        }

        // Log information about the touch event
        Log.i(TAG, "$action at x=${current.x}, y=${current.y}, width=${currentBox?.width}, height=${currentBox?.height}")

        // Indicate that the touch event has been handled
        return true
    }

    override fun onDraw(canvas: Canvas) {
        // Fill the background
        canvas.drawPaint(backgroundPaint)

        boxes.forEach { box ->
            // original code //
            //canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)

            // code for part 2 //
            //canvas.drawRect(box.left, box.top, box.left+box.width, box.top+box.width, boxPaint)       // only draw squares



            // Modified code for Part 3: Draw rectangles while dragging, squares if width > height, and circles otherwise
            if (drawing && currentBox == box) {
                // Draw rectangle while dragging
                canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
            } else {
                // Draw square if width > height
                if (box.width > box.height) {
                    canvas.drawRect(box.left, box.top, box.left + box.width, box.top + box.width, boxPaint)
                }
                // Draw circle otherwise
                else {
                    canvas.drawOval(box.left, box.top, box.left + box.width, box.top + box.width, boxPaint)
                }
            }
        }
    }

    // Update the current box with the latest touch point
    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            // Invalidate the view to trigger a redraw
            invalidate()
        }
    }
}
