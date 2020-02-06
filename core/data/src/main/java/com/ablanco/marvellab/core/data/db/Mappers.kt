package com.ablanco.marvellab.core.data.db

import com.ablanco.marvellab.core.data.db.model.CharacterEntity
import com.ablanco.marvellab.core.data.db.model.ComicEntity
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */

fun CharacterEntity.toDomain() = Character(characterId, name, description, imageUrl)

fun Character.toEntity() = CharacterEntity(id, name, description, imageUrl)

fun ComicEntity.toDomain() = Comic(comicId, title, releaseDate, coverImageUrl)

fun Comic.toEntity() = ComicEntity(id, title, releaseDate, coverImageUrl)