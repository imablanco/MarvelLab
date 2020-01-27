package com.ablanco.marvellab.features.home.presentation

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.domain.model.config.HomeSection
import com.ablanco.marvellab.core.domain.repository.ConfigRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */


@ActivityScope
class HomeViewModelFactory @Inject constructor(private val configRepository: ConfigRepository) :
    BaseViewModelFactory<HomeViewModel>() {

    override fun create(): HomeViewModel = HomeViewModel(configRepository)
}

data class HomeViewState(
    val isLoading: Boolean = false,
    val bottomItems: List<HomeSectionUi> = emptyList()
) : ViewState

class HomeViewModel(
    private val configRepository: ConfigRepository
) : LoaderViewModel<HomeViewState, HomeViewAction>() {

    override val initialViewState: HomeViewState = HomeViewState()

    override fun load() {
        launch {
            setState { copy(isLoading = true) }
            val config = configRepository.getHomeConfig().getOrNull()
            val sections = config?.sections?.sortedBy(HomeSection::type).orEmpty()
            setState { copy(isLoading = true, bottomItems = sections.map(HomeSection::toUi)) }
        }
    }

}