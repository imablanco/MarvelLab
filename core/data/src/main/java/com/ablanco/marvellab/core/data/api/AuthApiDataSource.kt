package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
class AuthApiDataSource @Inject constructor() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun isUserLogged(): Resource<Boolean> = Success(firebaseAuth.currentUser != null)

    suspend fun login(username: String, password: String): Resource<Boolean> =
        suspendCancellableCoroutine { cont ->
            firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (cont.isActive) cont.resume(successOf(task.isSuccessful))
                }
        }

    suspend fun signUp(username: String, password: String): CompletableResource =
        suspendCancellableCoroutine { cont ->
            firebaseAuth.createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener { if (cont.isActive) cont.resume(completed()) }
                .addOnFailureListener { if (cont.isActive) cont.resume(failOf(it)) }
        }

    fun logout(): CompletableResource {
        firebaseAuth.signOut()
        return completed()
    }
}