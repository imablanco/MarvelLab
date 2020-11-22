package com.ablanco.marvellab.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

/**
 * Created by Álvaro Blanco Cabrero on 22/11/2020.
 * MarvelLab.
 */
abstract class ComposeFragment : Fragment() {

    private var isRestored = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState, isRestored)
        isRestored = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent { compose() }
        }
    }

    abstract fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean)

    @Composable
    abstract fun compose()
}