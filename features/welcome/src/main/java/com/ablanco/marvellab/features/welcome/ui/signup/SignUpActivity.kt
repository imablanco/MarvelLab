package com.ablanco.marvellab.features.welcome.ui.signup

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.domain.extensions.withIO
import com.ablanco.marvellab.core.ui.GlideApp
import com.ablanco.marvellab.core.ui.extensions.saveToFile
import com.ablanco.marvellab.core.ui.extensions.scale
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.features.welcome.R
import com.ablanco.marvellab.features.welcome.di.DaggerSignUpComponent
import com.ablanco.marvellab.features.welcome.presentation.signup.PickPhotoAction
import com.ablanco.marvellab.features.welcome.presentation.signup.SignUpViewModelFactory
import com.ablanco.marvellab.features.welcome.presentation.signup.SingUpViewModel
import com.ablanco.marvellab.features.welcome.presentation.signup.UserSignedUpAction
import com.ablanco.marvellab.shared.navigation.Home
import com.ablanco.marvellab.shared.navigation.featureNavigator
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModelFactory

    private val viewModel: SingUpViewModel by viewModels { signUpViewModelFactory }

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
        ivPhoto.setOnClickListener { viewModel.pickPhotoClicked() }

        viewModel.viewState.observe(this, Observer { state ->
            progressBar.switchVisibility(state.isLoading)
            tilEmail.error =
                getString(R.string.sign_up_invalid_email).takeIf { state.isEmailInvalid }
            tilPassword.error =
                getString(R.string.sign_up_invalid_password).takeIf { state.isPasswordInvalid }
            btSignUp.isEnabled = state.canContinue
            GlideApp.with(this)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(state.profilePictureUrl)
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(ivPhoto)
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

                is PickPhotoAction -> pickPhoto()
            }
        })
    }


    private fun pickPhoto() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(PermissionListener {
                ImageProvider(this).getImage(ImageSource.CAMERA) { bitmap ->
                    bitmap?.let {
                        lifecycleScope.launch {
                            val file = withIO {
                                bitmap
                                    .scale(IMAGE_MAX_SIZE_PX)
                                    .saveToFile(this@SignUpActivity)
                            }
                            file?.let(viewModel::onProfilePicture)
                        }
                    }
                }
            }).check()
    }

    companion object {
        private const val IMAGE_MAX_SIZE_PX = 512
        fun start(context: Context) {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }
}
