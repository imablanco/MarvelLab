package com.ablanco.marvellab.core.data.api

import android.net.Uri
import com.ablanco.marvellab.core.domain.model.*
import com.ablanco.marvellab.core.domain.model.user.User
import com.ablanco.marvellab.core.domain.model.user.UserUpdate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
class UserApiDataSource @Inject constructor() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    @ExperimentalCoroutinesApi
    private val currentUserChannel: ConflatedBroadcastChannel<Resource<User>> =
        ConflatedBroadcastChannel<Resource<User>>().apply {
            val user = firebaseAuth.currentUser?.let { successOf(it.toDomain()) }
                ?: failOf(UserNotPresentException)
            offer(user)
        }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun getUser(): Flow<Resource<User>> = currentUserChannel.asFlow()

    @ExperimentalCoroutinesApi
    suspend fun updateUser(userUpdate: UserUpdate): CompletableResource =
        suspendCancellableCoroutine { cont ->

            val request = UserProfileChangeRequest.Builder()
                .setDisplayName(userUpdate.name)
                .setPhotoUri(userUpdate.profileUrl?.let { Uri.parse(it) })
                .build()
            firebaseAuth.currentUser?.updateProfile(request)
                ?.addOnSuccessListener {
                    firebaseAuth.currentUser?.let {
                        currentUserChannel.offer(successOf(it.toDomain()))
                    }
                    cont.resume(completed())
                }
                ?.addOnFailureListener { cont.resume(failed(it)) }
                ?: run { cont.resume(failed(UserNotPresentException)) }
        }

    companion object {
        val UserNotPresentException = IllegalStateException()
    }
}