package com.ablanco.marvellab.features.profile.ui

import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-29.
 * MarvelLab.
 */

fun PermissionListener(block: () -> Unit) = object : BasePermissionListener() {
    override fun onPermissionGranted(response: PermissionGrantedResponse?) = block()
}