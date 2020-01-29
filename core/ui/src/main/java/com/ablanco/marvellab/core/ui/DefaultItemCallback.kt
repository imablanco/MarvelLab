package com.ablanco.marvellab.core.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-29.
 * MarvelLab.
 */
class DefaultItemCallback<T>(private val idSelector: (T) -> Any? = { null }) :
    DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        idSelector(oldItem) == idSelector(newItem)

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}