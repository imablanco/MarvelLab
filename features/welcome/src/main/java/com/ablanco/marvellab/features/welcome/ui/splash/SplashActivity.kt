package com.ablanco.marvellab.features.welcome.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.presentation.viewModel
import com.ablanco.marvellab.features.welcome.R
import com.ablanco.marvellab.features.welcome.di.DaggerSplashComponent
import com.ablanco.marvellab.features.welcome.presentation.splash.IsUserLoggedAction
import com.ablanco.marvellab.features.welcome.presentation.splash.SplashViewModel
import com.ablanco.marvellab.features.welcome.presentation.splash.SplashViewModelFactory
import com.ablanco.marvellab.features.welcome.ui.welcome.WelcomeActivity
import com.ablanco.marvellab.shared.navigation.Home
import com.ablanco.marvellab.shared.navigation.featureNavigator
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory

    private val viewModel: SplashViewModel by viewModel { splashViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        DaggerSplashComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)

        viewModel.viewAction.observe(this, Observer { action ->
            when (action) {
                is IsUserLoggedAction -> {
                    if (action.isLogged) {
                        featureNavigator?.getIntent(Home)?.let(::startActivity)
                    } else {
                        WelcomeActivity.start(this)
                    }
                }
            }
        })

        if (savedInstanceState == null) {
            viewModel.load()
        }
    }
}
