package com.ablanco.marvellab.features.welcome.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.core.ui.viewbinding.binding
import com.ablanco.marvellab.features.welcome.R
import com.ablanco.marvellab.features.welcome.databinding.ActivityLoginBinding
import com.ablanco.marvellab.features.welcome.di.DaggerLoginComponent
import com.ablanco.marvellab.features.welcome.presentation.login.LoginViewModel
import com.ablanco.marvellab.features.welcome.presentation.login.LoginViewModelFactory
import com.ablanco.marvellab.features.welcome.presentation.login.UserLoggedAction
import com.ablanco.marvellab.shared.navigation.Home
import com.ablanco.marvellab.shared.navigation.featureNavigator
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    private val viewModel: LoginViewModel by viewModels { loginViewModelFactory }

    private val binding: ActivityLoginBinding by binding(ActivityLoginBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerLoginComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)

        binding.etEmail.doAfterTextChanged { it?.toString()?.let(viewModel::onEmail) }
        binding.etPassword.doAfterTextChanged { it?.toString()?.let(viewModel::onPassword) }
        binding.btLogin.setOnClickListener { viewModel.login() }

        viewModel.viewState.observe(this, Observer { state ->
            binding.progressBar.switchVisibility(state.isLoading)
            binding.tilEmail.error =
                getString(R.string.sign_up_invalid_email).takeIf { state.isEmailInvalid }
            binding.tilPassword.error =
                getString(R.string.sign_up_invalid_password).takeIf { state.isPasswordInvalid }
            binding.btLogin.isEnabled = state.canContinue
        })

        viewModel.viewAction.observe(this, Observer { action ->
            when (action) {
                is UserLoggedAction -> {
                    if (action.success) {
                        featureNavigator?.getIntent(Home)?.let(::startActivity)
                    } else {
                        Snackbar.make(
                            binding.rootLayout,
                            getString(R.string.sign_up_process_error),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
