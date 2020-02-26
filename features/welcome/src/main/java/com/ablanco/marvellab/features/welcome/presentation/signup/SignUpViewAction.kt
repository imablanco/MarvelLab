package com.ablanco.marvellab.features.welcome.presentation.signup

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-23.
 * MarvelLab.
 */
sealed class SignUpViewAction : ViewAction

data class UserSignedUpAction(val success: Boolean) : SignUpViewAction()
object PickPhotoAction : SignUpViewAction()