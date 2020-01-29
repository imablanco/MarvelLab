package com.ablanco.marvellab.features.home.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.ui.extensions.clearBackStack
import com.ablanco.marvellab.core.ui.extensions.isAtRoot
import com.ablanco.marvellab.core.ui.extensions.replace
import com.ablanco.marvellab.features.home.R
import com.ablanco.marvellab.features.home.di.DaggerHomeComponent
import com.ablanco.marvellab.features.home.presentation.HomeViewModel
import com.ablanco.marvellab.features.home.presentation.HomeViewModelFactory
import com.ablanco.marvellab.shared.navigation.featureNavigator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        DaggerHomeComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)

        viewModel.viewState.observe(this, Observer { state ->
            bottomNavigationView.menu.clear()
            state.bottomItems.forEachIndexed { index, section ->
                bottomNavigationView.menu.add(0, index, index, section.name).apply {
                    section.icon?.let { iconUrl ->
                        Glide.with(this@HomeActivity).load(iconUrl).into(MenuIconTarget(this))
                    }
                }
            }

            bottomNavigationView.setOnNavigationItemSelectedListener { menu ->
                val sectionFeature = state.bottomItems[menu.itemId].feature
                    ?: return@setOnNavigationItemSelectedListener false

                featureNavigator?.run {
                    getFragment(sectionFeature)?.let {
                        clearBackStack()
                        replace(R.id.container, it)
                        true
                    } ?: getIntent(sectionFeature)?.let {
                        startActivity(it)
                        false
                    } ?: false
                } ?: false
            }

            bottomNavigationView.selectedItemId = 0
        })

        viewModel.load()
    }

    override fun onBackPressed() {
        if (isAtRoot) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
