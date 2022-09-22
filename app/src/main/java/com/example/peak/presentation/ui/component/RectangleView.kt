package com.example.peak.presentation.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import android.view.View
import com.example.peak.data.storage.RectangleEntity
import com.example.peak.presentation.uitl.setScale
import kotlin.random.Random


/**
 * @author yaya (@yahyalmh)
 * @since 18th September 2022
 */

@SuppressLint("ViewConstructor", "ClickableViewAccessibility")
class RectangleView(
    context: Context,
    private val rectangle: RectangleEntity,
    private val oncPositionChangeListener: ((rectangle: RectangleEntity) -> Unit)
) : View(context) {
    private var xDown: Float = 0f
    private var yDown: Float = 0f
    private var windowWidth: Int = 0
    private var windowHeight: Int = 0
    private var rectangleColor: Int = 0

    init {
        rectangleColor = rectangle.color ?: getRandomColor()

        setBackground()

        setTouchListener()
    }

    private fun setTouchListener() {
        setOnTouchListener { _, event ->
            when (event.actionMasked) {

                MotionEvent.ACTION_DOWN -> {
                    xDown = event.x
                    yDown = event.y
                }

                MotionEvent.ACTION_MOVE -> {
                    val newX = x + (event.x - xDown)
                    if (newX > 0 && newX + width < windowWidth) x = newX

                    val newY = y + (event.y - yDown)
                    if (newY > 0 && newY + height < windowHeight) y = newY
                }

                MotionEvent.ACTION_UP -> {
                    val biasX = (x / (windowWidth - width)).setScale(3)
                    val biasY = (y / (windowHeight - height)).setScale(3)

                    saveRectangle(biasX, biasY)
                }
            }
            true
        }
    }

    private fun saveRectangle(biasX: Float, biasY: Float) {
        oncPositionChangeListener(
            rectangle.copy(
                x = biasX,
                y = biasY,
                color = rectangleColor
            )
        )
    }

    private fun setBackground() {
        background = GradientDrawable().apply {
            cornerRadius = 20f
            color = ColorStateList.valueOf(rectangleColor)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        windowWidth = MeasureSpec.getSize(widthMeasureSpec)
        windowHeight = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(
            windowWidth.times(rectangle.size).toInt(),
            windowHeight.times(rectangle.size).toInt()
        )
    }

    private fun getRandomColor(): Int {
        return Color.rgb(180, Random.nextInt(256), Random.nextInt(256))
    }

}
