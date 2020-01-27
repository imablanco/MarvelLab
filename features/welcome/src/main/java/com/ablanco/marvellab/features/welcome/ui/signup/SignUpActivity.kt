package com.ablanco.marvellab.features.welcome.ui.signup

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
import com.ablanco.marvellab.features.welcome.di.DaggerSignUpComponent
import com.ablanco.marvellab.features.welcome.presentation.signup.SignUpViewModelFactory
import com.ablanco.marvellab.features.welcome.presentation.signup.SingUpViewModel
import com.ablanco.marvellab.features.welcome.presentation.signup.UserSignedUpAction
import com.ablanco.marvellab.shared.navigation.Home
import com.ablanco.marvellab.shared.navigation.featureNavigator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModelFactory

    private val viewModel: SingUpViewModel by viewModel { signUpViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        DaggerSignUpComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)

        etEmail.doAfterTextChanged { it?.toString()?.let(viewModel::onEmail) }
        etPassword.doAfterTextChanged { it?.toString()?.let(viewModel::onPassword) }
        btSignUp.setOnClickListener { viewModel.signUp() }

        viewModel.viewState.observe(this, Observer { state ->
            progressBar.switchVisibility(state.isLoading)
            tilEmail.error =
                getString(R.string.sign_up_invalid_email).takeIf { state.isEmailInvalid }
            tilPassword.error =
                getString(R.string.sign_up_invalid_password).takeIf { state.isPasswordInvalid }
            btSignUp.isEnabled = state.canContinue
        })

        viewModel.viewAction.observe(this, Observer { action ->
            when (action) {
                is UserSignedUpAction -> {
                    if (action.success) {
                        featureNavigator?.getIntent(Home)?.let(::startActivity)
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
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }
}
