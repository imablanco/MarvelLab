package com.ablanco.marvellab.features.home.presentation

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */


@ActivityScope
class HomeViewModelFactory @Inject constructor() : BaseViewModelFactory<HomeViewModel>() {

    override fun create(): HomeViewModel = HomeViewModel()
}

object HomeViewState : ViewState

class HomeViewModel : LoaderViewModel<HomeViewState, HomeViewAction>() {

    override val initialViewState: HomeViewState =
        HomeViewState

    override fun load() {
        //TBD
    }

}