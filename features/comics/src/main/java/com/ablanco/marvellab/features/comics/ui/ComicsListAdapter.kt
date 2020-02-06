package com.ablanco.marvellab.features.comics.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.ui.DefaultItemCallback
import com.ablanco.marvellab.features.comics.R
import com.bumptech.glide.Glide

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */
class ComicsListAdapter(private val onComicClicked: (Comic) -> Unit) :
    ListAdapter<Comic, ComicsListAdapter.ComicViewHolder>(DefaultItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder =
        ComicViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_comic,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivComic: ImageView = itemView.findViewById(R.id.ivComic)
        private val tvComicName: TextView = itemView.findViewById(R.id.tvComicName)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onComicClicked(getItem(adapterPosition))
                }
            }
        }

        fun bind(comic: Comic) {
            Glide.with(itemView.context).load(comic.coverImageUrl).into(ivComic)
            tvComicName.text = comic.title
        }
    }
}