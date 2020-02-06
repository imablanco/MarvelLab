package com.ablanco.marvellab

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.ablanco.marvellab.features.characters.ui.detail.CharacterDetailFragment
import com.ablanco.marvellab.features.characters.ui.list.CharactersListFragment
import com.ablanco.marvellab.features.comics.ui.ComicsListFragment
import com.ablanco.marvellab.features.home.ui.HomeActivity
import com.ablanco.marvellab.features.profile.ui.ProfileFragment
import com.ablanco.marvellab.features.welcome.ui.splash.SplashActivity
import com.ablanco.marvellab.shared.navigation.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-27.
 * MarvelLab.
 */

class FeatureNavigatorImpl(private val context: Context) : FeatureNavigator {

    override fun getIntent(feature: Feature): Intent? =
        when (feature) {
            is Welcome -> intentOf<SplashActivity>(context).inNewTask()
            is Home -> intentOf<HomeActivity>(context).inNewTask()
            else -> null
        }

    override fun getFragment(feature: Feature): Fragment? =
        when (feature) {
            is Profile -> ProfileFragment()
            is Characters -> {
                feature.characterId?.let { CharacterDetailFragment.newInstance(it) }
                    ?: CharactersListFragment()
            }
            is Comics -> {
                feature.comicId?.let { null } ?: ComicsListFragment()
            }
            else -> null
        }
}

private inline fun <reified T : Activity> intentOf(context: Context) =
    Intent(context, T::class.java)

private fun Intent.inNewTask() =
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)