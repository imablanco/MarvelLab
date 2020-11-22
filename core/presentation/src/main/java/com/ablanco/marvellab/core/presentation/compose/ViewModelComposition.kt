package com.ablanco.marvellab.core.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.staticAmbientOf
import com.ablanco.marvellab.core.presentation.BaseViewModel
import com.ablanco.marvellab.core.presentation.ViewAction
import com.ablanco.marvellab.core.presentation.ViewState

val ViewStateAmbient = staticAmbientOf<ViewState>()

@Composable
fun <V : ViewState, A : ViewAction> ViewModelComposition(
    viewModel: BaseViewModel<V, A>,
    content: @Composable (V) -> Unit
) {
    val viewState = viewModel.viewState.observeAsState().value ?: throw IllegalStateException()
    Providers(ViewStateAmbient provides viewState, children = { content(viewState) })
}

@Composable
inline fun <reified V : ViewState> currentViewState(): V = ViewStateAmbient.current as V