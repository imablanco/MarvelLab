package com.ablanco.marvellab.features.characters.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.marvellab.core.ui.DefaultItemCallback
import com.ablanco.marvellab.core.ui.GlideApp
import com.ablanco.marvellab.features.characters.R
import com.ablanco.marvellab.features.characters.presentation.common.ComicPresentation

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-03.
 * MarvelLab.
 */
class CharacterComicsAdapter(
    private val onComicClicked: (ComicPresentation) -> Unit,
    private val onFavoriteClicked: (ComicPresentation) -> Unit
) : ListAdapter<ComicPresentation, CharacterComicsAdapter.ComicViewHolder>(DefaultItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder =
        ComicViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_character_comic,
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
        private val cbFavorite: CheckBox = itemView.findViewById(R.id.cbFavorite)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onComicClicked(getItem(adapterPosition))
                }
            }
            cbFavorite.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onFavoriteClicked(getItem(adapterPosition))
                }
            }
        }

        fun bind(item: ComicPresentation) {
            GlideApp.with(itemView.context).load(item.comic.coverImageUrl).into(ivComic)
            tvComicName.text = item.comic.title
            cbFavorite.isChecked = item.isFavorite
        }
    }
}