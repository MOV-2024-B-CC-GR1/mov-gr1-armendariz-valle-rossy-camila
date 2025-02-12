package com.example.proyecto_ruleta

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class RouletteWheelView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var options = mutableListOf<String>()
    private var sweepAngles = mutableListOf<Float>()
    private var rotationAngle = 0f

    fun setupWheelOptions(newOptions: List<String>) {
        if (newOptions.isEmpty()) return
        this.options = newOptions.toMutableList()
        calculateAngles()
        invalidate()
    }

    private fun calculateAngles() {
        sweepAngles.clear()
        val anglePerSection = 360f / options.size
        repeat(options.size) { sweepAngles.add(anglePerSection) }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (options.isEmpty()) return

        val radius = (min(width, height) / 2 * 0.8).toFloat()
        val centerX = width / 2f
        val centerY = height / 2f

        var startAngle = rotationAngle
        for ((index, angle) in sweepAngles.withIndex()) {
            paint.color = getColorForIndex(index)
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle,
                angle,
                true,
                paint
            )

            drawTextOnArc(canvas, options[index], centerX, centerY, radius, startAngle, angle)
            startAngle += angle
        }
    }

    private fun drawTextOnArc(
        canvas: Canvas,
        text: String,
        cx: Float,
        cy: Float,
        radius: Float,
        startAngle: Float,
        sweepAngle: Float
    ) {
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = radius * 0.15f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        val textRadius = radius * 0.7f  // Posiciona el texto más dentro del segmento
        val textAngle = startAngle + (sweepAngle / 2)  // Centra el texto en la sección

        val textX = (cx + textRadius * cos(Math.toRadians(textAngle.toDouble()))).toFloat()
        val textY = (cy + textRadius * sin(Math.toRadians(textAngle.toDouble()))).toFloat()

        val metrics = textPaint.fontMetrics
        val verticalOffset = -(metrics.ascent + metrics.descent) / 2

        canvas.drawText(text.uppercase(), textX, textY + verticalOffset, textPaint)
    }

    fun rotateWheel(targetRotations: Float = 5F, duration: Long = 2000) {
        if (options.isEmpty()) return

        val finalAngle = 360f * targetRotations + Math.random().toFloat() * 360

        val animator = ValueAnimator.ofFloat(rotationAngle, rotationAngle + finalAngle).apply {
            this.duration = duration
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                rotationAngle = it.animatedValue as Float
                invalidate()
            }
        }
        animator.start()
    }

    private fun getColorForIndex(index: Int): Int {
        val colors = listOf(
            ContextCompat.getColor(context, R.color.roulette_red),
            ContextCompat.getColor(context, R.color.roulette_blue),
            ContextCompat.getColor(context, R.color.roulette_green),
            ContextCompat.getColor(context, R.color.roulette_yellow),
            ContextCompat.getColor(context, R.color.roulette_orange),
            ContextCompat.getColor(context, R.color.roulette_purple)

        )
        return colors[index % colors.size]
    }
}
