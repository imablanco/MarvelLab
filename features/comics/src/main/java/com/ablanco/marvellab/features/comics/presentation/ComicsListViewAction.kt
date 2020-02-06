package com.ablanco.marvellab.features.comics.presentation

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */

sealed class ComicsListViewAction : ViewAction

data class GoToComicDetailAction(val comicId: String) : ComicsListViewAction()