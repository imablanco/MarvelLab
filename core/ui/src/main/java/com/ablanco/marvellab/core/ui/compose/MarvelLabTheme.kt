package com.ablanco.marvellab.core.ui.compose

import androidx.compose.runtime.Composable
import com.google.android.material.composethemeadapter.MdcTheme

/**
 * Created by Álvaro Blanco Cabrero on 22/11/2020.
 * MarvelLab.
 */

@Composable
fun MarvelLabTheme(content: @Composable () -> Unit) {
    MdcTheme(content = content)
}