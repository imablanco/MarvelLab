package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.data.api.model.HomeConfigData
import com.ablanco.marvellab.core.data.fromJson
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.config.HomeConfig
import com.ablanco.marvellab.core.domain.model.failOf
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
class ConfigApiDataSource @Inject constructor() {

    private val remoteConfig: FirebaseRemoteConfig by lazy {
        FirebaseRemoteConfig.getInstance()
    }

    suspend fun getHomeConfig(): Resource<HomeConfig> =
        suspendCancellableCoroutine { cont ->
            //TODO change fetch interval in release
            remoteConfig.fetch(1)
                .continueWithTask<Boolean> { remoteConfig.activate() }
                .addOnSuccessListener {
                    val result = remoteConfig.getString(HOME)
                        .fromJson<HomeConfigData>()
                        .map { it.toDomain() }
                    cont.resumeIfActive(result)
                }
                .addOnFailureListener { cont.resumeIfActive(failOf(it)) }
        }


    companion object {
        private const val HOME = "home"
    }
}