package com.ablanco.marvellab.features.welcome.presentation.login

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.domain.repository.AuthRepository
import com.ablanco.marvellab.core.presentation.*
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */

@ActivityScope
class LoginViewModelFactory @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModelFactory<LoginViewModel>() {

    override fun create(): LoginViewModel = LoginViewModel(authRepository)
}

data class LoginViewState(
    val email: String? = null,
    val password: String? = null,
    val isEmailInvalid: Boolean = false,
    val isPasswordInvalid: Boolean = false,
    val canContinue: Boolean = false,
    val isLoading: Boolean = false
) : ViewState

class LoginViewModel(private val authRepository: AuthRepository) :
    BaseViewModel<LoginViewState, LoginViewAction>() {

    override val initialViewState: LoginViewState = LoginViewState()

    private val emailValidator = EmailValidator()
    private val passwordValidator = MinLengthTextValidator(PASSWORD_MIN_LENGTH)

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

    fun login() {
        val username = getState().email ?: return
        val password = getState().password ?: return

        launch {
            setState { copy(isLoading = true) }
            val loginSuccessful = authRepository.login(username, password).getOrNull() ?: false
            dispatchAction(UserLoggedAction(loginSuccessful))
            setState { copy(isLoading = false) }
        }
    }

    companion object {
        private const val PASSWORD_MIN_LENGTH = 6
    }
}