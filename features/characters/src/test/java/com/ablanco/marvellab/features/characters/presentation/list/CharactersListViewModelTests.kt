package com.ablanco.marvellab.features.characters.presentation.list

import com.ablanco.marvellab.core.domain.extensions.toFavorite
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.failOf
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.model.favorites.FavoriteType
import com.ablanco.marvellab.core.domain.model.successOf
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import com.ablanco.marvellab.core.domain.repository.FavoritesRepository
import com.ablanco.marvellab.core.test.BaseViewModelTest
import com.ablanco.marvellab.core.test.TestObserver
import com.ablanco.marvellab.core.test.randomId
import com.ablanco.marvellab.features.characters.presentation.common.CharacterPresentation
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

/**
 * Created by Álvaro Blanco Cabrero on 01/05/2020.
 * MarvelLab.
 */
@ExperimentalCoroutinesApi
class CharactersListViewModelTests : BaseViewModelTest() {

    @Test
    fun `GIVEN idle state WHEN load is called THEN ViewState is loading`() {

        val viewModel = CharactersListViewModel(
            mockCharactersRepository(successOf(emptyList())),
            mockFavoritesRepository(successOf(emptyList()))
        )

        val observer = TestObserver<CharactersListViewState>()
        viewModel.viewState.observeForever(observer)
        viewModel.load()

        assertTrue(observer.thirdValue.isLoading)
    }

    @Test
    fun `GIVEN characters WHEN load is called THEN ViewState contains characters`() {

        val characters = listOf(
            Character(randomId(), null, null, null)
        )
        val viewModel = CharactersListViewModel(
            mockCharactersRepository(successOf(characters)),
            mockFavoritesRepository(successOf(emptyList()))
        )

        val observer = TestObserver<CharactersListViewState>()
        viewModel.viewState.observeForever(observer)
        viewModel.load()

        assertTrue(observer.fourthValue.characters.isNotEmpty())
    }

    @Test
    fun `GIVEN no characters WHEN load is called THEN ViewState does not contains characters`() {

        val viewModel = CharactersListViewModel(
            mockCharactersRepository(failOf(Throwable())),
            mockFavoritesRepository(successOf(emptyList()))
        )

        val observer = TestObserver<CharactersListViewState>()
        viewModel.viewState.observeForever(observer)
        viewModel.load()

        assertTrue(observer.fourthValue.characters.isEmpty())
    }

    @Test
    fun `GIVEN characters and favorites WHEN load is called THEN ViewState contains characters and favorites`() {

        val characters = listOf(
            Character(randomId(), null, null, null)
        )
        val favorites = listOf(
            characters.first().toFavorite()
        )
        val viewModel = CharactersListViewModel(
            mockCharactersRepository(successOf(characters)),
            mockFavoritesRepository(successOf(favorites))
        )

        val observer = TestObserver<CharactersListViewState>()
        viewModel.viewState.observeForever(observer)
        viewModel.load()

        val value = observer.fourthValue.characters
        assertTrue(value.isNotEmpty())
        assertTrue(value.any(CharacterPresentation::isFavorite))
    }

    @Test
    fun `GIVEN characters and no favorites WHEN load is called THEN ViewState contains characters but not favorites`() {

        val characters = listOf(
            Character(randomId(), null, null, null)
        )
        val viewModel = CharactersListViewModel(
            mockCharactersRepository(successOf(characters)),
            mockFavoritesRepository(successOf(emptyList()))
        )

        val observer = TestObserver<CharactersListViewState>()
        viewModel.viewState.observeForever(observer)
        viewModel.load()

        val value = observer.fourthValue.characters
        assertTrue(value.isNotEmpty())
        assertTrue(value.none(CharacterPresentation::isFavorite))
    }

    @Test
    fun `GIVEN favorites and no characters WHEN load is called THEN ViewState does not contains characters`() {

        val favorites = listOf(
            Favorite(randomId(), null, null, FavoriteType.Character)
        )
        val viewModel = CharactersListViewModel(
            mockCharactersRepository(successOf(emptyList())),
            mockFavoritesRepository(successOf(favorites))
        )

        val observer = TestObserver<CharactersListViewState>()
        viewModel.viewState.observeForever(observer)
        viewModel.load()

        assertTrue(observer.fourthValue.characters.isEmpty())
    }

    @Test
    fun `GIVEN characters WHEN characters is clicked THEN ViewAction is notified`() {

        val characters = listOf(
            Character(randomId(), null, null, null)
        )
        val viewModel = CharactersListViewModel(
            mockCharactersRepository(successOf(characters)),
            mockFavoritesRepository(successOf(emptyList()))
        )

        val observer = TestObserver<CharactersListViewAction>()
        viewModel.viewAction.observeForever(observer)
        viewModel.load()
        viewModel.characterClicked(viewModel.getState().characters.first())

        val action = observer.firstValue
        assertTrue(
            action is GoToCharacterDetail &&
                    action.characterId == viewModel.getState().characters.first().character.id
        )
    }

    @FlowPreview
    @Test
    fun `GIVEN a favorite character WHEN favorite is clicked THEN character is removed as favorite`() {

        val characters = listOf(
            Character(randomId(), null, null, null)
        )
        val favorites = listOf(
            characters.first().toFavorite()
        )
        val favoritesFlow = ConflatedBroadcastChannel(successOf(favorites))
        val favoritesRepository = mock<FavoritesRepository> {
            onBlocking { getAllFavorites() } doReturn favoritesFlow.asFlow()
            onBlocking { removeFavorite(any()) } doAnswer {
                /*Need to simulate was fav repo does under the hood*/
                favoritesFlow.sendBlocking(successOf(emptyList()))
                successOf(true)
            }
        }

        val viewModel = CharactersListViewModel(
            mockCharactersRepository(successOf(characters)),
            favoritesRepository
        )

        val observer = TestObserver<CharactersListViewState>()
        viewModel.viewState.observeForever(observer)
        viewModel.load()
        viewModel.favoriteClicked(viewModel.getState().characters.first())

        assertTrue(observer.fourthValue.characters.any(CharacterPresentation::isFavorite))
        assertTrue(observer.fifthValue.characters.none(CharacterPresentation::isFavorite))
    }

    @FlowPreview
    @Test
    fun `GIVEN no favorites WHEN favorite is clicked THEN character is added as favorite`() {

        val characters = listOf(
            Character(randomId(), null, null, null)
        )
        val favoritesFlow = ConflatedBroadcastChannel(successOf(emptyList<Favorite>()))
        val favoritesRepository = mock<FavoritesRepository> {
            onBlocking { getAllFavorites() } doReturn favoritesFlow.asFlow()
            onBlocking { addFavorite(any()) } doAnswer {
                /*Need to simulate was fav repo does under the hood*/
                favoritesFlow.sendBlocking(successOf(listOf(characters.first().toFavorite())))
                successOf(true)
            }
        }

        val viewModel = CharactersListViewModel(
            mockCharactersRepository(successOf(characters)),
            favoritesRepository
        )

        val observer = TestObserver<CharactersListViewState>()
        viewModel.viewState.observeForever(observer)
        viewModel.load()
        viewModel.favoriteClicked(viewModel.getState().characters.first())

        assertTrue(observer.fourthValue.characters.none(CharacterPresentation::isFavorite))
        assertTrue(observer.fifthValue.characters.any(CharacterPresentation::isFavorite))
    }

    private fun mockCharactersRepository(doReturn: Resource<List<Character>>) =
        mock<CharactersRepository> {
            onBlocking { searchCharacters(anyOrNull(), anyInt()) } doReturn flowOf(doReturn)
        }

    private fun mockFavoritesRepository(doReturn: Resource<List<Favorite>>) =
        mock<FavoritesRepository> {
            onBlocking { getAllFavorites() } doReturn flowOf(doReturn)
        }
}
