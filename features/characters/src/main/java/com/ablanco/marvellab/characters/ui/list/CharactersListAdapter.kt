package com.ablanco.marvellab.characters.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.marvellab.characters.R
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.ui.DefaultItemCallback
import com.bumptech.glide.Glide

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-29.
 * MarvelLab.
 */
class CharactersListAdapter(private val onCharacterClicked: (Character) -> Unit) :
    ListAdapter<Character, CharactersListAdapter.CharacterHolder>(DefaultItemCallback()) {

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

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onCharacterClicked(getItem(adapterPosition))
                }
            }
        }

        fun bind(character: Character) {
            Glide.with(itemView.context).load(character.imageUrl).into(ivCharacter)
            tvCharacterName.text = character.name
        }
    }
}