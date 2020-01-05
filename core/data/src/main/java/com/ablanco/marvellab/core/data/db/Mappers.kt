package com.ablanco.marvellab.core.data.db

import com.ablanco.marvellab.core.data.db.model.CharacterEntity
import com.ablanco.marvellab.core.domain.model.Character

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */

fun CharacterEntity.toDomain() = Character(characterId, name, description, imageUrl)
fun Character.toEntity() = CharacterEntity(id, name, description, imageUrl)