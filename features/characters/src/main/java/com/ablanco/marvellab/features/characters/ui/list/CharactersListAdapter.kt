package com.ablanco.marvellab.features.characters.ui.list

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
import com.ablanco.marvellab.features.characters.presentation.list.CharacterPresentation

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-29.
 * MarvelLab.
 */
class CharactersListAdapter(
    private val onCharacterClicked: (CharacterPresentation) -> Unit,
    private val onFavoriteClicked: (CharacterPresentation) -> Unit
) : ListAdapter<CharacterPresentation, CharactersListAdapter.CharacterHolder>(DefaultItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder =
        CharacterHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_character,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharacterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCharacter: ImageView = itemView.findViewById(R.id.ivCharacter)
        private val tvCharacterName: TextView = itemView.findViewById(R.id.tvCharacterName)
        private val cbFavorite: CheckBox = itemView.findViewById(R.id.cbFavorite)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onCharacterClicked(getItem(adapterPosition))
                }
            }
            cbFavorite.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onFavoriteClicked(getItem(adapterPosition))
                }
            }
        }

        fun bind(item: CharacterPresentation) {
            val character = item.character
            val fallbackColors = itemView.context.resources.getIntArray(R.array.fallbackColors)
            itemView.setBackgroundColor(fallbackColors[adapterPosition % fallbackColors.size])
            GlideApp.with(itemView.context).load(character.imageUrl).into(ivCharacter)
            tvCharacterName.text = character.name
            cbFavorite.isChecked = item.isFavorite
        }
    }
}