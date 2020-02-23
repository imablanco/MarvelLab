package com.ablanco.marvellab.features.favorites.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.model.favorites.FavoriteType
import com.ablanco.marvellab.core.ui.DefaultItemCallback
import com.ablanco.marvellab.core.ui.GlideApp
import com.ablanco.marvellab.features.favorites.R

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-23.
 * MarvelLab.
 */
class FavoritesAdapter(
    private val onFavoriteClicked: (Favorite) -> Unit,
    private val onChangeFavorite: (Favorite) -> Unit
) : ListAdapter<Favorite, FavoritesAdapter.FavoriteHolder>(DefaultItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder =
        FavoriteHolder(
            LayoutInflater.from(parent.context).inflate(
                viewType,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).favoriteType) {
            FavoriteType.Character -> R.layout.item_favorite_character
            FavoriteType.Comic -> R.layout.item_favorite_comic
        }
    }

    inner class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivFavorite: ImageView = itemView.findViewById(R.id.ivFavorite)
        private val tvFavoriteName: TextView = itemView.findViewById(R.id.tvFavoriteName)
        private val cbFavorite: CheckBox = itemView.findViewById(R.id.cbFavorite)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onFavoriteClicked(getItem(adapterPosition))
                }
            }
            cbFavorite.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onChangeFavorite(getItem(adapterPosition))
                }
            }
        }

        fun bind(favorite: Favorite) {
            GlideApp.with(itemView.context).load(favorite.imageUrl).into(ivFavorite)
            tvFavoriteName.text = favorite.name
            cbFavorite.isChecked = true
        }
    }
}