package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.*
import com.ablanco.marvellab.core.domain.model.user.User
import com.ablanco.marvellab.core.domain.model.user.UserUpdate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
class UserApiDataSource @Inject constructor() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firebaseStorage: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    @ExperimentalCoroutinesApi
    private val userChannel: ConflatedBroadcastChannel<Resource<User>> =
        ConflatedBroadcastChannel<Resource<User>>().apply {
            val user = firebaseAuth.currentUser?.let { successOf(it.toDomain()) }
                ?: failOf(UserNotPresentException)
            offer(user)
        }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun getUser(): Flow<Resource<User>> = userChannel.asFlow()

    @ExperimentalCoroutinesApi
    suspend fun updateUser(userUpdate: UserUpdate): CompletableResource =
        suspendCancellableCoroutine { cont ->
            val request = UserProfileChangeRequest.Builder()
                .setDisplayName(userUpdate.name)
                .build()
            updateUser(request, cont)
        }

    @ExperimentalCoroutinesApi
    suspend fun uploadUserProfilePicture(byteArray: ByteArray): CompletableResource {
        val userId = firebaseAuth.currentUser?.uid ?: return failed(UserNotPresentException)
        val reference = firebaseStorage.reference.child("$USERS_BUCKET_PATH/$userId.png")
        return suspendCancellableCoroutine { cont ->
            reference.putBytes(byteArray).continueWithTask { reference.downloadUrl }
                .addOnSuccessListener { url ->
                    val request = UserProfileChangeRequest.Builder()
                        .setPhotoUri(url)
                        .build()
                    updateUser(request, cont)
                }
                .addOnFailureListener { cont.resumeIfActive(failed(it)) }
        }
    }

    @ExperimentalCoroutinesApi
    private fun updateUser(
        request: UserProfileChangeRequest,
        cont: CancellableContinuation<CompletableResource>
    ) {
        firebaseAuth.currentUser?.updateProfile(request)
            ?.addOnSuccessListener {
                firebaseAuth.currentUser?.let { userChannel.offer(successOf(it.toDomain())) }
                cont.resumeIfActive(completed())
            }
            ?.addOnFailureListener { cont.resumeIfActive(failed(it)) }
            ?: run { cont.resumeIfActive(failed(UserNotPresentException)) }
    }

    companion object {
        private const val USERS_BUCKET_PATH = "users/profile"
    }
}