package org.jcastrejon.features.list.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import org.jcastrejon.arch.collectAsStateLifecycleAware
import org.jcastrejon.arch.rememberFlow
import org.jcastrejon.features.list.ui.arch.*
import org.jcastrejon.features.list.ui.arch.ListAction.LoadData
import org.jcastrejon.features.list.ui.arch.ListEffect.GoToAddNote

@Composable
fun ListScreen(
    listViewModel: ListViewModel = viewModel(),
    onAddNoteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state: ListState by listViewModel.state.collectAsStateLifecycleAware(ListState())
    val effects: Flow<ListEffect> = rememberFlow(listViewModel.viewEffects)

    LaunchedEffect(key1 = true) {
        listViewModel.onAction(LoadData)
    }

    LaunchedEffect(true) {
        effects.collect { effect ->
            when (effect) {
                is GoToAddNote -> onAddNoteClick()
            }
        }
    }

    ListContent(
        modifier = modifier,
        state = state,
        onAction = { action -> listViewModel.onAction(action) }
    )
}


@Composable
fun ListContent(
    modifier: Modifier,
    state: ListState,
    onAction: (ListAction) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Crossfade(targetState = state.viewState) { viewState ->
            when (viewState) {
                ViewState.Loading -> ListLoadingView()
                ViewState.Empty -> ListEmptyView()
                ViewState.List -> ListView(notes = state.notes)
            }
        }
    }
}

@Composable
fun ListView(
    modifier: Modifier = Modifier,
    notes: List<Any> = emptyList()
) {

}

@Composable
fun ListLoadingView(
    modifier: Modifier = Modifier
) {

}

@Composable
fun ListEmptyView(
    modifier: Modifier = Modifier
) {

}

