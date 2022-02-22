package com.gergo225.hydrationapp.ui.home

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.BindingAdapter
import com.gergo225.hydrationapp.R

class GlassView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var filledPercentage = 0
    private var maskHeight = 0

    private val fillBitmap: Bitmap

    private lateinit var maskedBitmap: Bitmap
    private lateinit var tempCanvas: Canvas

    private val fillPaint = Paint()
    private val maskPaint = Paint()

    private var leftOffset = 0f
    private var topOffset = 0f

    init {
        val fillDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_glass_full, null)
        fillBitmap = fillDrawable?.toBitmap()!!

        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        maskPaint.alpha = 0

        // Custom attributes
        context.withStyledAttributes(attrs, R.styleable.GlassView) {
            filledPercentage = getInteger(R.styleable.GlassView_fillPercentage, 0)
            fillPaint.alpha =
                (255 * getFloat(R.styleable.GlassView_fillOpacity, 1f)).toInt().coerceIn(0, 255)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        maskedBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        tempCanvas = Canvas(maskedBitmap)

        leftOffset = (w / 2 - fillBitmap.width / 2).toFloat()
        topOffset = (h / 2 - fillBitmap.height / 2).toFloat()

        updatePercentage(filledPercentage)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        maskedBitmap.eraseColor(Color.TRANSPARENT)
        tempCanvas.drawBitmap(fillBitmap, 0f, 0f, fillPaint)
        tempCanvas.drawRect(
            0f,
            0f,
            maskedBitmap.width.toFloat(),
            maskHeight.toFloat(),
            maskPaint
        )

        canvas.drawBitmap(maskedBitmap, leftOffset, topOffset, null)
    }

    fun setFilledPercentage(newPercentage: Int) {
        // needs redrawing only if it's not already full
        val needsRedraw = filledPercentage < 100

        filledPercentage = newPercentage
        if (needsRedraw) {
            calculateMaskHeight(newPercentage)
            invalidate()
            requestLayout()
        }
    }

    private fun calculateMaskHeight(percentage: Int) {
        val fillHeight: Int = (height * (percentage / 100f)).toInt()
        maskHeight = height - fillHeight
    }

}

@BindingAdapter("fillPercentage")
fun GlassView.updatePercentage(percentage: Int) {
    setFilledPercentage(percentage)
}