package com.ablanco.marvellab.shared.navigation

import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-27.
 * MarvelLab.
 */
interface FeatureNavigator {

    fun getIntent(feature: Feature): Intent?
    fun getFragment(feature: Feature): Fragment?
}


interface FeatureNavigatorOwner {

    val featureNavigator: FeatureNavigator
}