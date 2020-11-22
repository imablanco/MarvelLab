package com.ablanco.marvellab.core.ui.compose

import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.ui.tooling.preview.Preview

/**
 * Created by Álvaro Blanco Cabrero on 22/11/2020.
 * MarvelLab.
 */
@Composable
fun SearchView(onValueChange: (String) -> Unit) =
    Card(backgroundColor = Color.White) {
        TextField(value = "", onValueChange = onValueChange)
    }

@Preview
@Composable
fun SearchViewPreview() =SearchView({})