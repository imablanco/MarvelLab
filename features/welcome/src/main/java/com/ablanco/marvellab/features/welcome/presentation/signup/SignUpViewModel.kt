package com.ablanco.marvellab.features.welcome.presentation.signup

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.domain.extensions.withIO
import com.ablanco.marvellab.core.domain.model.Success
import com.ablanco.marvellab.core.domain.model.auth.SignUp
import com.ablanco.marvellab.core.domain.repository.AuthRepository
import com.ablanco.marvellab.core.presentation.*
import java.io.File
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-23.
 * MarvelLab.
 */

@ActivityScope
class SignUpViewModelFactory @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModelFactory<SingUpViewModel>() {

    override fun create(): SingUpViewModel = SingUpViewModel(authRepository)
}

data class SignUpViewState(
    val email: String? = null,
    val password: String? = null,
    val isEmailInvalid: Boolean = false,
    val isPasswordInvalid: Boolean = false,
    val canContinue: Boolean = false,
    val isLoading: Boolean = false,
    val profilePictureUrl: String? = null
) : ViewState

class SingUpViewModel(private val authRepository: AuthRepository) :
    BaseViewModel<SignUpViewState, SignUpViewAction>() {

    override val initialViewState: SignUpViewState = SignUpViewState()

    private val emailValidator = EmailValidator()
    private val passwordValidator = MinLengthTextValidator(PASSWORD_MIN_LENGTH)
    private var profilePicture: ByteArray? = null

    fun onEmail(text: String) {
        setState {
            val isValid = emailValidator.isValid(text)
            copy(
                email = text,
                isEmailInvalid = !isValid,
                canContinue = isValid && !isPasswordInvalid
            )
        }
    }

    fun onPassword(text: String) {
        setState {
            val isValid = passwordValidator.isValid(text)
            copy(
                password = text,
                isPasswordInvalid = !isValid,
                canContinue = isValid && !isEmailInvalid
            )
        }
    }


    fun onProfilePicture(file: File) {
        launch {
            profilePicture = withIO { file.readBytes() }
            setState { copy(profilePictureUrl = file.absolutePath) }
        }
    }

    fun signUp() {
        val email = getState().email ?: return
        val password = getState().password ?: return

        launch {
            setState { copy(isLoading = true, canContinue = false) }
            val signUp = SignUp(email, password, profilePicture)
            val signUpSuccessful = authRepository.signUp(signUp) is Success
            dispatchAction(UserSignedUpAction(signUpSuccessful))
            setState { copy(isLoading = false, canContinue = true) }
        }
    }

    fun pickPhotoClicked() = dispatchAction(PickPhotoAction)

    companion object {
        private const val PASSWORD_MIN_LENGTH = 6
    }
}