package org.jcastrejon.features.list.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import org.jcastrejon.arch.collectAsStateLifecycleAware
import org.jcastrejon.arch.rememberFlow
import org.jcastrejon.features.list.R
import org.jcastrejon.features.list.ui.arch.*
import org.jcastrejon.features.list.ui.arch.ListAction.*
import org.jcastrejon.features.list.ui.arch.ListEffect.GoToAddNote
import org.jcastrejon.features.list.ui.arch.ListEffect.GoToDetail
import org.jcastrejon.features.list.ui.components.ListEmptyView
import org.jcastrejon.features.list.ui.components.ListLoadingView
import org.jcastrejon.features.list.ui.components.ListToolbar
import org.jcastrejon.features.list.ui.components.ListView

@Composable
fun ListScreen(
    listViewModel: ListViewModel = viewModel(),
    onAddNoteClick: () -> Unit = {},
    onNoteClick: (Int) -> Unit = {},
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
                is GoToDetail -> onNoteClick(effect.id)
            }
        }
    }

    ListContent(
        modifier = modifier,
        state = state,
        onAction = listViewModel::onAction
    )
}


@Composable
fun ListContent(
    modifier: Modifier,
    state: ListState,
    onAction: (ListAction) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .imePadding()
    ) {
        ListToolbar(
            title = stringResource(id = R.string.toobar_title),
            editMode = state.editMode,
            onEditModeClick = { onAction(EditNoteClick) }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Crossfade(targetState = state.viewState) { viewState ->
                when (viewState) {
                    ViewState.Loading -> ListLoadingView()
                    ViewState.Empty -> ListEmptyView()
                    ViewState.List -> ListView(
                        notes = state.notes,
                        editMode = state.editMode,
                        selectedNotes = state.selectedNotes,
                        onNoteClick = { noteId ->
                            onAction(NoteClick(id = noteId, editMode = state.editMode))
                        }
                    )
                }
            }
            SlideIn(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding(),
                visible = !state.editMode,
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(vertical = 24.dp, horizontal = 16.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    onClick = { onAction(AddNoteClick) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            }
            SlideIn(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding(),
                visible = state.editMode && state.selectedNotes.isNotEmpty(),
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(vertical = 24.dp, horizontal = 16.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    onClick = { onAction(DeleteClick(ids = state.selectedNotes)) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun SlideIn(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn() + slideInVertically { it },
        exit = slideOutVertically { it } + fadeOut(),
        content = content
    )
}
