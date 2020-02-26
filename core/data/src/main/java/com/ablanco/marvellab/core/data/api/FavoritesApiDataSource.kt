package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.failOf
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.model.successOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
class FavoritesApiDataSource @Inject constructor() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val collection: CollectionReference by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
    }

    @ExperimentalCoroutinesApi
    fun getFavorites(): Flow<Resource<List<Favorite>>> = channelFlow {
        firebaseAuth.currentUser?.let { user ->
            val listener = collection
                .whereEqualTo(FireStoreFavoriteFields.FIELD_USER_ID, user.uid)
                .orderBy(FireStoreFavoriteFields.FIELD_TIMESTAMP)
                .addSnapshotListener { snapshot, e ->
                    snapshot?.let { results ->
                        val favorites = results.documents
                            .mapNotNull { runCatching { FavoriteMapMapper.run { it.data?.fromMap() } }.getOrNull() }
                            .map { it.toDomain() }
                        channel.offer(successOf(favorites))
                    }
                    e?.let {
                        channel.offer(failOf(it))
                    }
                }

            awaitClose { listener.remove() }

        } ?: send(failOf(UserNotPresentException))
    }

    suspend fun addFavorite(favorite: Favorite): Resource<Boolean> {
        return firebaseAuth.currentUser?.let { user ->
            return suspendCancellableCoroutine { cont ->
                val favoriteData = favorite.toData(user.uid)
                collection.document(favoriteData.fireStoreId)
                    .set(FavoriteMapMapper.run { favoriteData.toMap() })
                    .addOnCompleteListener { cont.resumeIfActive(successOf(it.isSuccessful)) }
            }
        } ?: failOf(UserNotPresentException)
    }

    suspend fun removeFavorite(favorite: Favorite): Resource<Boolean> {
        return firebaseAuth.currentUser?.let { user ->
            return suspendCancellableCoroutine { cont ->
                val favoriteData = favorite.toData(user.uid)
                collection.document(favoriteData.fireStoreId)
                    .delete()
                    .addOnCompleteListener { cont.resumeIfActive(successOf(it.isSuccessful)) }
            }
        } ?: failOf(UserNotPresentException)
    }

    companion object {
        private const val COLLECTION_NAME = "favorites"
    }
}