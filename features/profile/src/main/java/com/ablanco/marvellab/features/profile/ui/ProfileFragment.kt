package com.ablanco.marvellab.features.profile.ui

import android.os.Bundle
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.presentation.viewModel
import com.ablanco.marvellab.core.ui.BaseCollapsingToolbarFragment
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import com.ablanco.marvellab.features.profile.R
import com.ablanco.marvellab.features.profile.di.DaggerProfileComponent
import com.ablanco.marvellab.features.profile.presentation.ProfileViewModel
import com.ablanco.marvellab.features.profile.presentation.ProfileViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
class ProfileFragment : BaseCollapsingToolbarFragment(R.layout.fragment_profile) {

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory

    private val viewModel: ProfileViewModel by viewModel { profileViewModelFactory }

    override val toolbarConfig: ToolbarConfig by lazy {
        SimpleToolbarConfig(
            title = getString(R.string.profile_title),
            menu = R.menu.menu_profile,
            onMenuClickListener = {
                when (it.itemId) {
                    R.id.action_change_photo -> {
                    }
                }
                true
            }
        )
    }

    override val toolbarView: CollapsingToolbarLayout by lazy { collapsingToolbarLayout }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        DaggerProfileComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)

        etName.doAfterTextChanged { it?.toString()?.let(viewModel::nameEntered) }
        btSave.setOnClickListener { viewModel.save() }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            viewLoading.switchVisibility(state.isLoading)
            if (state.email.orEmpty() != etEmail.text?.toString()) {
                etEmail.setText(state.email, TextView.BufferType.EDITABLE)
            }
            if (state.name.orEmpty() != etName.text?.toString()) {
                etName.setText(state.name, TextView.BufferType.EDITABLE)
            }
            tilName.error = getString(R.string.profile_error_name).takeIf { !state.isNameValid }
            btSave.isEnabled = state.canSave
            toolbar.menu.findItem(R.id.action_change_photo)?.isEnabled = state.canEditPhoto
            Glide.with(this)
                .load(state.userPictureUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(ivProfile)
        })

        if (savedInstanceState == null) {
            viewModel.load()
        }
    }

}