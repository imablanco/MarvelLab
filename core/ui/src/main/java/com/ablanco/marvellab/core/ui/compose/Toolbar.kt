package com.ablanco.marvellab.core.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

/**
 * Created by Álvaro Blanco Cabrero on 22/11/2020.
 * MarvelLab.
 */

private val ToolbarHeight = 56.dp

@Composable
fun Toolbar(title: String) =
    TopAppBar(modifier = Modifier.preferredHeight(ToolbarHeight)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Text(modifier = Modifier.padding(horizontal = 16.dp), text = title)
        }
    }

@Preview
@Composable
fun ToolbarPreview() = Toolbar(title = "Example")