package com.gigih.android.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.gigih.android.R

class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {

    private val defaultRadius = 100f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var cornerRadius = defaultRadius

    init {
        attributeSet?.let {
            val styledAttributes = context.obtainStyledAttributes(it, R.styleable.RoundedImageView, 0, 0)
            cornerRadius = styledAttributes.getDimension(R.styleable.RoundedImageView_cornerRadius, defaultRadius)
            styledAttributes.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(cornerRadius, cornerRadius, cornerRadius, paint)
        super.onDraw(canvas)
    }
}