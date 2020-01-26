package com.ablanco.marvellab.features.welcome.di

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.features.welcome.presentation.login.LoginViewModelFactory
import com.ablanco.marvellab.features.welcome.ui.login.LoginActivity
import dagger.Component

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */
@ActivityScope
@Component(dependencies = [CoreComponent::class])
interface LoginComponent {

    val loginViewModelFactory: LoginViewModelFactory

    fun inject(loginActivity: LoginActivity)
}
