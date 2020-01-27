package com.ablanco.marvellab.shared.navigation

import android.content.Context
import android.content.ContextWrapper

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-27.
 * MarvelLab.
 */

val Context.featureNavigator: FeatureNavigator?
    get() = when (this) {
        is FeatureNavigatorOwner -> featureNavigator
        is ContextWrapper -> baseContext.featureNavigator
        else -> applicationContext.featureNavigator
    }