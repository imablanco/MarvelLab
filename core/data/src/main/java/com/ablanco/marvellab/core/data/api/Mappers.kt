package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.data.api.model.*
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.model.config.HomeConfig
import com.ablanco.marvellab.core.domain.model.config.HomeSection
import com.ablanco.marvellab.core.domain.model.config.HomeSectionType
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.model.favorites.FavoriteType
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
    HomeSectionTypeData.Favorites -> HomeSectionType.Favorites
}

fun HomeSectionData.toDomain() = HomeSection(name, icon, type?.toDomain(), order)

fun HomeConfigData.toDomain() = HomeConfig(sections?.map(HomeSectionData::toDomain))

/*The only way we to have to detect if the image is not valid is checking the literal
* that the API throws at us (image_not_available)*/
private const val ImageNotAvailableLiteral = "image_not_available"

fun ImageData.toDomain(): String? = "$path.$extension"
    .takeIf { !it.contains(ImageNotAvailableLiteral) }

fun List<DateData>.toDomain(typeData: DateTypeData): Date? = find { it.type == typeData }?.date

fun CharacterData.toDomain(): Character =
    Character(id.orEmpty(), name, description, thumbnail?.toDomain())

fun ComicData.toDomain(): Comic =
    Comic(id.orEmpty(), title, dates.toDomain(DateTypeData.OnSaleDate), thumbnail?.toDomain())

fun FavoriteData.toDomain(): Favorite = Favorite(
    favoriteId,
    name,
    imageUrl,
    when (type) {
        FavoriteTypeData.Character -> FavoriteType.Character
        FavoriteTypeData.Comic -> FavoriteType.Comic
    }
)

fun Favorite.toData(userId: String): FavoriteData = FavoriteData(
    userId,
    id,
    name,
    imageUrl,
    when (favoriteType) {
        FavoriteType.Character -> FavoriteTypeData.Character
        FavoriteType.Comic -> FavoriteTypeData.Comic
    }
)

interface MapMapper<T> {

    fun T.toMap(): Map<String, Any?>

    fun Map<String, Any?>.fromMap(): T
}

object FireStoreFavoriteFields {
    const val FIELD_USER_ID = "userId"
    const val FIELD_FAVORITE_ID = "favoriteId"
    const val FIELD_NAME = "name"
    const val FIELD_IMAGE = "imageUrl"
    const val FIELD_TYPE = "type"
}

object FavoriteMapMapper : MapMapper<FavoriteData> {

    override fun FavoriteData.toMap(): Map<String, Any?> = mapOf(
        FireStoreFavoriteFields.FIELD_USER_ID to userId,
        FireStoreFavoriteFields.FIELD_FAVORITE_ID to favoriteId,
        FireStoreFavoriteFields.FIELD_NAME to name,
        FireStoreFavoriteFields.FIELD_IMAGE to imageUrl,
        FireStoreFavoriteFields.FIELD_TYPE to type.number
    )

    override fun Map<String, Any?>.fromMap(): FavoriteData {
        val userId = get(FireStoreFavoriteFields.FIELD_USER_ID) as String
        val favoriteId = get(FireStoreFavoriteFields.FIELD_FAVORITE_ID) as String
        val name = get(FireStoreFavoriteFields.FIELD_NAME) as? String
        val imageUrl = get(FireStoreFavoriteFields.FIELD_IMAGE) as? String
        val type = FavoriteTypeData.values().first {
            it.number.toLong() == get(FireStoreFavoriteFields.FIELD_TYPE) as Long
        }
        return FavoriteData(userId, favoriteId, name, imageUrl, type)
    }

}