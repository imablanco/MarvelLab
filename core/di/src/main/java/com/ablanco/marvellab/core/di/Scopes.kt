package com.ablanco.marvellab.core.di

import javax.inject.Scope

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-08.
 * MarvelLab.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope