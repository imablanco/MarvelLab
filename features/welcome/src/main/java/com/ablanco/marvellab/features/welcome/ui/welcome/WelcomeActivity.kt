package com.ablanco.marvellab.features.welcome.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.ui.viewbinding.binding
import com.ablanco.marvellab.features.welcome.R
import com.ablanco.marvellab.features.welcome.databinding.ActivityWelcomeBinding
import com.ablanco.marvellab.features.welcome.di.DaggerWelcomeComponent
import com.ablanco.marvellab.features.welcome.presentation.welcome.LoginAction
import com.ablanco.marvellab.features.welcome.presentation.welcome.SignUpAction
import com.ablanco.marvellab.features.welcome.presentation.welcome.WelcomeViewModel
import com.ablanco.marvellab.features.welcome.presentation.welcome.WelcomeViewModelFactory
import com.ablanco.marvellab.features.welcome.ui.login.LoginActivity
import com.ablanco.marvellab.features.welcome.ui.signup.SignUpActivity
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity(R.layout.activity_welcome) {

    @Inject
    lateinit var welcomeViewModelFactory: WelcomeViewModelFactory

    private val viewModel: WelcomeViewModel by viewModels { welcomeViewModelFactory }

    private val binding: ActivityWelcomeBinding by binding(ActivityWelcomeBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerWelcomeComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)


        binding.btLogin.setOnClickListener { viewModel.loginClicked() }
        binding.btSignUp.setOnClickListener { viewModel.signUpClicked() }

        viewModel.viewAction.observe(this, Observer { action ->
            when (action) {
                is SignUpAction -> SignUpActivity.start(this)
                is LoginAction -> LoginActivity.start(this)
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
