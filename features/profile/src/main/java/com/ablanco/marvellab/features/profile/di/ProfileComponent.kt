package com.ablanco.marvellab.features.profile.di

import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.di.FragmentScope
import dagger.Component

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface ProfileComponent {


}
