package com.ablanco.marvellab.features.profile.ui

import android.Manifest
import android.os.Bundle
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.domain.extensions.withIO
import com.ablanco.marvellab.core.ui.BaseCollapsingToolbarFragment
import com.ablanco.marvellab.core.ui.GlideApp
import com.ablanco.marvellab.core.ui.extensions.bytes
import com.ablanco.marvellab.core.ui.extensions.scale
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import com.ablanco.marvellab.core.ui.viewbinding.binding
import com.ablanco.marvellab.features.profile.R
import com.ablanco.marvellab.features.profile.databinding.FragmentProfileBinding
import com.ablanco.marvellab.features.profile.di.DaggerProfileComponent
import com.ablanco.marvellab.features.profile.presentation.LogoutAction
import com.ablanco.marvellab.features.profile.presentation.PickPhotoAction
import com.ablanco.marvellab.features.profile.presentation.ProfileViewModel
import com.ablanco.marvellab.features.profile.presentation.ProfileViewModelFactory
import com.ablanco.marvellab.shared.navigation.Welcome
import com.ablanco.marvellab.shared.navigation.featureNavigator
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.karumi.dexter.Dexter
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
class ProfileFragment : BaseCollapsingToolbarFragment(R.layout.fragment_profile) {

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory

    private val viewModel: ProfileViewModel by viewModels { profileViewModelFactory }

    private val binding: FragmentProfileBinding by binding(FragmentProfileBinding::bind)

    override val toolbarConfig: ToolbarConfig by lazy {
        SimpleToolbarConfig(
            title = getString(R.string.profile_title),
            menu = R.menu.menu_profile,
            onMenuClickListener = {
                when (it.itemId) {
                    R.id.action_change_photo -> viewModel.updatePhotoClicked()
                }
                true
            }
        )
    }

    override val getToolbarView: () -> CollapsingToolbarLayout = { binding.collapsingToolbarLayout }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerProfileComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {

        binding.etName.doAfterTextChanged { it?.toString()?.let(viewModel::nameEntered) }
        binding.btSave.setOnClickListener { viewModel.save() }
        binding.btLogout.setOnClickListener { viewModel.logOutClicked() }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            binding.viewLoading.switchVisibility(state.isLoading)
            if (state.email.orEmpty() != binding.etEmail.text?.toString()) {
                binding.etEmail.setText(state.email, TextView.BufferType.EDITABLE)
            }
            if (state.name.orEmpty() != binding.etName.text?.toString()) {
                binding.etName.setText(state.name, TextView.BufferType.EDITABLE)
            }
            binding.tilName.error =
                getString(R.string.profile_error_name).takeIf { !state.isNameValid }
            binding.btSave.isEnabled = state.canSave
            binding.toolbar.menu.findItem(R.id.action_change_photo)?.isEnabled = state.canEditPhoto
            GlideApp.with(this)
                .load(state.userPictureUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(binding.ivProfile)
        })

        viewModel.viewAction.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is PickPhotoAction -> pickPhoto()
                is LogoutAction -> requireContext().featureNavigator?.getIntent(Welcome)
                    ?.let(requireContext()::startActivity)
            }
        })

        if (!isRestored) {
            viewModel.load()
        }
    }

    private fun pickPhoto() {
        Dexter.withActivity(requireActivity())
            .withPermission(Manifest.permission.CAMERA)
            .withListener(PermissionListener {
                ImageProvider(requireActivity()).getImage(ImageSource.CAMERA) { bitmap ->
                    bitmap?.let {
                        lifecycleScope.launch {
                            val data = withIO { bitmap.scale(IMAGE_MAX_SIZE_PX).bytes() }
                            viewModel.updateUserPhoto(data)
                        }
                    }
                }
            }).check()
    }

    companion object {
        private const val IMAGE_MAX_SIZE_PX = 512
    }
}