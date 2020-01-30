package com.ablanco.marvellab.core.ui.views

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-30.
 * MarvelLab.
 */
class EndScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val onBottomReached: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    private var isLoading = false
    private var lastItemCount = 0

    @Suppress("UNCHECKED_CAST")
    private val itemsOwner: ItemsOwner<RecyclerView.LayoutManager> =
        when (layoutManager) {
            is LinearLayoutManager -> LinearItemsOwner() as ItemsOwner<RecyclerView.LayoutManager>
            is GridLayoutManager -> GridItemsOwner() as ItemsOwner<RecyclerView.LayoutManager>
            else -> throw IllegalStateException("LayoutManager type not supported")
        }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (layoutManager.itemCount == 0) return

        if (isLoading && layoutManager.itemCount > lastItemCount) {
            isLoading = false
        }
        if (!isLoading) {
            val lastVisibleItem = itemsOwner.run { layoutManager.lastVisibleItem }
            val diff = (layoutManager.itemCount - 1) - lastVisibleItem
            if (diff == 0) {
                isLoading = true
                onBottomReached(layoutManager.itemCount)
            }
        }
        lastItemCount = layoutManager.itemCount
    }

    private interface ItemsOwner<T : RecyclerView.LayoutManager> {

        val T.firstVisibleItem: Int
        val T.lastVisibleItem: Int
    }

    private class LinearItemsOwner : ItemsOwner<LinearLayoutManager> {

        override val LinearLayoutManager.firstVisibleItem: Int
            get() = findFirstVisibleItemPosition()
        override val LinearLayoutManager.lastVisibleItem: Int
            get() = findLastVisibleItemPosition()
    }

    private class GridItemsOwner : ItemsOwner<GridLayoutManager> {

        override val GridLayoutManager.firstVisibleItem: Int
            get() = findFirstVisibleItemPosition()
        override val GridLayoutManager.lastVisibleItem: Int
            get() = findLastVisibleItemPosition()
    }
}