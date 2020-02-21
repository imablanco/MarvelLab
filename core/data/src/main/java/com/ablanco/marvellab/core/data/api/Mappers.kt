package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.data.api.model.*
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.model.config.HomeConfig
import com.ablanco.marvellab.core.domain.model.config.HomeSection
import com.ablanco.marvellab.core.domain.model.config.HomeSectionType
import com.ablanco.marvellab.core.domain.model.user.User
import com.google.firebase.auth.FirebaseUser
import java.util.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */

fun FirebaseUser.toDomain(): User =
    User(
        uid,
        displayName,
        email,
        photoUrl?.toString()
    )

fun HomeSectionTypeData.toDomain() = when (this) {
    HomeSectionTypeData.Characters -> HomeSectionType.Characters
    HomeSectionTypeData.Comics -> HomeSectionType.Comics
    HomeSectionTypeData.Profile -> HomeSectionType.Profile
}

fun HomeSectionData.toDomain() = HomeSection(name, icon, type?.toDomain(), order)

fun HomeConfigData.toDomain() = HomeConfig(sections?.map(HomeSectionData::toDomain))

fun ImageData.toDomain(): String = "$path.$extension"

fun List<DateData>.toDomain(typeData: DateTypeData): Date? = find { it.type == typeData }?.date

fun CharacterData.toDomain(): Character =
    Character(id.orEmpty(), name, description, thumbnail?.toDomain())

fun ComicData.toDomain(): Comic =
    Comic(id.orEmpty(), title, dates.toDomain(DateTypeData.onSaleDate), thumbnail?.toDomain())