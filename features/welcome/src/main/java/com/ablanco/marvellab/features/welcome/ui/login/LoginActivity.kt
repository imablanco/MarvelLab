package com.ablanco.marvellab.features.welcome.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.presentation.viewModel
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.features.welcome.R
import com.ablanco.marvellab.features.welcome.di.DaggerLoginComponent
import com.ablanco.marvellab.features.welcome.presentation.login.LoginViewModel
import com.ablanco.marvellab.features.welcome.presentation.login.LoginViewModelFactory
import com.ablanco.marvellab.features.welcome.presentation.login.UserLoggedAction
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    private val viewModel: LoginViewModel by viewModel { loginViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        DaggerLoginComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)

        etEmail.doAfterTextChanged { it?.toString()?.let(viewModel::onEmail) }
        etPassword.doAfterTextChanged { it?.toString()?.let(viewModel::onPassword) }
        btLogin.setOnClickListener { viewModel.login() }

        viewModel.viewState.observe(this, Observer { state ->
            progressBar.switchVisibility(state.isLoading)
            tilEmail.error =
                getString(R.string.sign_up_invalid_email).takeIf { state.isEmailInvalid }
            tilPassword.error =
                getString(R.string.sign_up_invalid_password).takeIf { state.isPasswordInvalid }
            btLogin.isEnabled = state.canContinue
        })

        viewModel.viewAction.observe(this, Observer { action ->
            when (action) {
                is UserLoggedAction -> {
                    if (action.success) {
                        //TODO go to home!
                    } else {
                        Snackbar.make(
                            rootLayout,
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
