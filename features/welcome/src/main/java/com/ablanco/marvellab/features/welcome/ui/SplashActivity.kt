package com.ablanco.marvellab.features.welcome.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.presentation.viewModel
import com.ablanco.marvellab.features.welcome.R
import com.ablanco.marvellab.features.welcome.di.DaggerSplashComponent
import com.ablanco.marvellab.features.welcome.presentation.SplashViewModel
import com.ablanco.marvellab.features.welcome.presentation.SplashViewModelFactory
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory

    private val viewModel: SplashViewModel by viewModel(splashViewModelFactory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        DaggerSplashComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)

        if(savedInstanceState == null) {
            viewModel.load()
        }
    }
}
