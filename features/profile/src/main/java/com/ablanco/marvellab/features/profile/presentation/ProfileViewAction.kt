package com.ablanco.marvellab.features.profile.presentation

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
sealed class ProfileViewAction : ViewAction

object ErrorSavingAction : ProfileViewAction()
object PickPhotoAction : ProfileViewAction()
object LogoutAction : ProfileViewAction()