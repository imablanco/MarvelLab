package com.ablanco.marvellab.features.welcome.presentation.login

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
sealed class LoginViewAction : ViewAction

data class UserLoggedAction(val success: Boolean) : LoginViewAction()