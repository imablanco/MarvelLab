package com.ablanco.marvellab.features.welcome.presentation.splash

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
sealed class SplashViewAction : ViewAction

class IsUserLoggedAction(val isLogged : Boolean) : SplashViewAction()