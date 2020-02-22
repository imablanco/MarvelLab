package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.failOf
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.model.successOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

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

    fun getFavorites(): Flow<Resource<List<Favorite>>> = flow {
        firebaseAuth.currentUser?.let { user ->
            val favorites = suspendCancellableCoroutine<Resource<List<Favorite>>> { cont ->
                collection
                    .whereEqualTo(FireStoreFavoriteFields.FIELD_USER_ID, user.uid)
                    .addSnapshotListener { snapshot, e ->
                        snapshot?.let { results ->
                            val favorites = results.documents
                                .mapNotNull { FavoriteMapMapper.run { it.data?.fromMap() } }
                                .map { it.toDomain() }
                            cont.resume(successOf(favorites))
                        }
                        e?.let {
                            cont.resume(failOf(it))
                        }
                    }
            }
            emit(favorites)
        } ?: emit(failOf(UserNotPresentException))
    }

    suspend fun addFavorite(favorite: Favorite): Resource<Boolean> {
        return firebaseAuth.currentUser?.let { user ->
            return suspendCancellableCoroutine { cont ->
                val favoriteData = favorite.toData(user.uid)
                collection.document(favoriteData.fireStoreId)
                    .set(FavoriteMapMapper.run { favoriteData.toMap() })
                    .addOnCompleteListener { cont.resume(successOf(it.isSuccessful)) }
            }
        } ?: failOf(UserNotPresentException)
    }

    suspend fun removeFavorite(favorite: Favorite): Resource<Boolean> {
        return firebaseAuth.currentUser?.let { user ->
            return suspendCancellableCoroutine { cont ->
                val favoriteData = favorite.toData(user.uid)
                collection.document(favoriteData.fireStoreId)
                    .delete()
                    .addOnCompleteListener { cont.resume(successOf(it.isSuccessful)) }
            }
        } ?: failOf(UserNotPresentException)
    }

    companion object {
        private const val COLLECTION_NAME = "favorites"
    }
}