package com.ablanco.marvellab.features.profile.presentation

import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.model.Success
import com.ablanco.marvellab.core.domain.model.user.UserUpdate
import com.ablanco.marvellab.core.domain.repository.UserRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.NonEmptyTextValidator
import com.ablanco.marvellab.core.presentation.ViewState
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */

@FragmentScope
class ProfileViewModelFactory @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModelFactory<ProfileViewModel>() {

    override fun create(): ProfileViewModel = ProfileViewModel(userRepository)
}

data class ProfileViewState(
    val isLoading: Boolean = false,
    val email: String? = null,
    val name: String? = null,
    val userPictureUrl: String? = null,
    val canSave: Boolean = false,
    val canEditPhoto: Boolean = false,
    val isNameValid: Boolean = true
) : ViewState

class ProfileViewModel(private val userRepository: UserRepository) :
    LoaderViewModel<ProfileViewState, ProfileViewAction>() {

    override val initialViewState: ProfileViewState = ProfileViewState()

    private val nameValidator = NonEmptyTextValidator()

    override fun load() {
        launch {
            setState { copy(isLoading = true, canSave = false) }
            userRepository.getUser().collect { resource ->
                val user = resource.getOrNull()
                setState {
                    copy(
                        isLoading = false,
                        canSave = false,
                        canEditPhoto = true,
                        email = user?.email,
                        name = user?.name,
                        userPictureUrl = user?.profileUrl
                    )
                }
            }
        }
    }

    fun nameEntered(text: String) {
        val isValid = nameValidator.isValid(text)
        setState { copy(name = text, isNameValid = isValid, canSave = isValid) }
    }

    fun save() {
        val name = getState().name ?: return
        launch {
            setState { copy(isLoading = true, canSave = false, canEditPhoto = false) }
            val isSuccess = userRepository.updateUser(UserUpdate(name)) is Success
            if (!isSuccess) dispatchAction(ErrorSavingAction)
            setState { copy(isLoading = false, canSave = true, canEditPhoto = true) }
        }
    }

    fun updatePhotoClicked() = dispatchAction(PickPhotoAction)

    fun updateUserPhoto(byteArray: ByteArray) {
        launch {
            setState { copy(isLoading = true, canSave = false, canEditPhoto = false) }
            val isSuccess = userRepository.uploadUserProfilePicture(byteArray) is Success
            if (!isSuccess) dispatchAction(ErrorSavingAction)
            setState { copy(isLoading = false, canSave = true, canEditPhoto = true) }
        }
    }
}