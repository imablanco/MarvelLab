package com.ablanco.marvellab.features.welcome.presentation.signup

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.domain.model.Success
import com.ablanco.marvellab.core.domain.repository.AuthRepository
import com.ablanco.marvellab.core.presentation.BaseViewModel
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.ViewState
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
    val isLoading: Boolean = false
) : ViewState

class SingUpViewModel(private val authRepository: AuthRepository) :
    BaseViewModel<SignUpViewState, SignUpViewAction>() {

    override val initialViewState: SignUpViewState = SignUpViewState()

    fun onEmail(text: String) {
        setState {
            //TODO email format
            val isValid = text.isNotBlank()
            copy(
                email = text,
                isEmailInvalid = !isValid,
                canContinue = isValid && !isPasswordInvalid
            )
        }
    }

    fun onPassword(text: String) {
        setState {
            val isValid = text.length >= PASSWORD_MIN_LENGTH
            copy(
                password = text,
                isPasswordInvalid = !isValid,
                canContinue = isValid && !isEmailInvalid
            )
        }
    }

    fun signUp() {
        val username = getState().email ?: return
        val password = getState().password ?: return

        launch {
            setState { copy(isLoading = true) }
            val signUpSuccessful = authRepository.signUp(username, password) is Success
            dispatchAction(UserSignedUpAction(signUpSuccessful))
        }
    }

    companion object {
        private const val PASSWORD_MIN_LENGTH = 6
    }
}