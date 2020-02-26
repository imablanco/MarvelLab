package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.*
import com.ablanco.marvellab.core.domain.model.auth.Login
import com.ablanco.marvellab.core.domain.model.auth.SignUp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
class AuthApiDataSource @Inject constructor(private val userApiDataSource: UserApiDataSource) {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun isUserLogged(): Resource<Boolean> = Success(firebaseAuth.currentUser != null)

    suspend fun login(login: Login): Resource<Boolean> =
        suspendCancellableCoroutine { cont ->
            firebaseAuth.signInWithEmailAndPassword(login.email, login.password)
                .addOnCompleteListener { task ->
                    cont.resumeIfActive(successOf(task.isSuccessful))
                }
        }

    @ExperimentalCoroutinesApi
    suspend fun signUp(signUp: SignUp): CompletableResource =
        suspendCancellableCoroutine<CompletableResource> { cont ->
            firebaseAuth.createUserWithEmailAndPassword(signUp.email, signUp.password)
                .addOnSuccessListener { cont.resumeIfActive(completed()) }
                .addOnFailureListener { cont.resumeIfActive(failed(it)) }
        }.fold(
            {
                signUp.profilePicture?.let { picture ->
                    userApiDataSource.uploadUserProfilePicture(picture)
                } ?: completed()
            },
            { failed(it) }
        )

    fun logout(): CompletableResource {
        firebaseAuth.signOut()
        return completed()
    }
}