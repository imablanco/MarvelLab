package com.ablanco.marvellab.features.welcome.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.presentation.viewModel
import com.ablanco.marvellab.features.welcome.R
import com.ablanco.marvellab.features.welcome.di.DaggerWelcomeComponent
import com.ablanco.marvellab.features.welcome.presentation.welcome.SignUpAction
import com.ablanco.marvellab.features.welcome.presentation.welcome.WelcomeViewModel
import com.ablanco.marvellab.features.welcome.presentation.welcome.WelcomeViewModelFactory
import com.ablanco.marvellab.features.welcome.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity() {

    @Inject
    lateinit var welcomeViewModelFactory: WelcomeViewModelFactory

    private val viewModel: WelcomeViewModel by viewModel { welcomeViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        DaggerWelcomeComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)


        btLogin.setOnClickListener { viewModel.loginClicked() }
        btSignUp.setOnClickListener { viewModel.signUpClicked() }

        viewModel.viewAction.observe(this, Observer { action ->
            when (action) {
                is SignUpAction -> SignUpActivity.start(this)
            }
        })
    }


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, WelcomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
