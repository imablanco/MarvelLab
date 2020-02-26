package com.ablanco.marvellab.features.welcome.ui.signup

import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener

fun PermissionListener(block: () -> Unit) = object : BasePermissionListener() {
    override fun onPermissionGranted(response: PermissionGrantedResponse?) = block()
}