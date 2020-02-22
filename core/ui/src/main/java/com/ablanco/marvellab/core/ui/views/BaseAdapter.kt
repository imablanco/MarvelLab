package com.ablanco.marvellab.core.ui.views

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.marvellab.core.ui.DefaultItemCallback

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T> = DefaultItemCallback()
) : ListAdapter<T, VH>(diffCallback) {

    /*Just make it public*/
    public override fun getItem(position: Int): T {
        return super.getItem(position)
    }
}