package com.ablanco.marvellab.features.welcome.presentation.welcome

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-23.
 * MarvelLab.
 */
sealed class WelcomeViewAction : ViewAction

object LoginAction : WelcomeViewAction()
object SignUpAction : WelcomeViewAction()