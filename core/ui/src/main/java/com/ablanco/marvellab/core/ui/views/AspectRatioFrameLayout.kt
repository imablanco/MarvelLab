package com.ablanco.marvellab.core.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.ablanco.marvellab.core.ui.R

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-03.
 * MarvelLab.
 */
class AspectRatioFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var aspectRatio: Float = 1f
        set(value) {
            field = value
            requestLayout()
        }

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioFrameLayout)
        aspectRatio = typedArray.getFloat(R.styleable.AspectRatioFrameLayout_ratio, 1f)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newHeight = MeasureSpec.getSize(widthMeasureSpec) / aspectRatio
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(newHeight.toInt(), MeasureSpec.EXACTLY)
        )
    }

}