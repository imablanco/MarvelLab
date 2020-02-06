package com.ablanco.marvellab.core.ui.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */
class GridSpacingItemDecorator(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val halfSpacing = spacing / 2
        outRect.set(halfSpacing, halfSpacing, halfSpacing, halfSpacing)
    }
}