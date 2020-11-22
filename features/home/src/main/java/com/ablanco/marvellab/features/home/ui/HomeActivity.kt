package com.ablanco.marvellab.features.home.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.ui.GlideApp
import com.ablanco.marvellab.core.ui.navigation.FragmentNavigator
import com.ablanco.marvellab.core.ui.navigation.FragmentNavigatorImpl
import com.ablanco.marvellab.core.ui.navigation.FragmentNavigatorOwner
import com.ablanco.marvellab.core.ui.viewbinding.binding
import com.ablanco.marvellab.features.home.R
import com.ablanco.marvellab.features.home.databinding.ActivityHomeBinding
import com.ablanco.marvellab.features.home.di.DaggerHomeComponent
import com.ablanco.marvellab.features.home.presentation.HomeViewModel
import com.ablanco.marvellab.features.home.presentation.HomeViewModelFactory
import com.ablanco.marvellab.features.home.presentation.InitializeBottomBarAction
import com.ablanco.marvellab.shared.navigation.featureNavigator
import javax.inject.Inject

class HomeActivity : AppCompatActivity(R.layout.activity_home), FragmentNavigatorOwner {

    override val fragmentNavigator: FragmentNavigator by lazy {
        FragmentNavigatorImpl(supportFragmentManager, R.id.container)
    }

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private val binding: ActivityHomeBinding by binding(ActivityHomeBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerHomeComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menu ->
            val sectionFeature = viewModel.getState().bottomItems[menu.itemId].feature
                ?: return@setOnNavigationItemSelectedListener false

            featureNavigator?.run {
                getFragment(sectionFeature)?.let {
                    fragmentNavigator.clearBackStack()
                    fragmentNavigator.navigate(it)
                    true
                } ?: getIntent(sectionFeature)?.let {
                    startActivity(it)
                    false
                } ?: false
            } ?: false
        }

        viewModel.viewState.observe(this, Observer { state ->
            binding.bottomNavigationView.menu.clear()
            state.bottomItems.forEachIndexed { index, section ->
                binding.bottomNavigationView.menu.add(0, index, index, section.name).apply {
                    GlideApp.with(this@HomeActivity)
                        .load(section.icon)
                        .into(MenuIconTarget(this))
                }
            }
        })

        viewModel.viewAction.observe(this, Observer { action ->
            when (action) {
                is InitializeBottomBarAction -> binding.bottomNavigationView.selectedItemId = 0
            }
        })

        if (savedInstanceState == null) {
            viewModel.load()
        }
    }

    override fun onBackPressed() {
        if (fragmentNavigator.isAtRoot) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
