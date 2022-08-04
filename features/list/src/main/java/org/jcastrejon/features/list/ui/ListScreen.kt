package org.jcastrejon.features.list.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import org.jcastrejon.arch.collectAsStateLifecycleAware
import org.jcastrejon.arch.rememberFlow
import org.jcastrejon.features.list.R
import org.jcastrejon.features.list.navigation.components.ListEmptyView
import org.jcastrejon.features.list.ui.arch.*
import org.jcastrejon.features.list.ui.arch.ListAction.LoadData
import org.jcastrejon.features.list.ui.arch.ListAction.AddNoteClick
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
    ) {
        ListToolbar(
            title = stringResource(id = R.string.toobar_title),
            filter = null
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Crossfade(targetState = state.viewState) { viewState ->
                when (viewState) {
                    ViewState.Loading -> ListLoadingView()
                    ViewState.Empty -> ListEmptyView()
                    ViewState.List -> ListView(notes = state.notes)
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                ,
                backgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                contentColor = MaterialTheme.colors.onBackground,
                onClick = { onAction(AddNoteClick) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
fun ListToolbar(
    modifier: Modifier = Modifier,
    title: String,
    filter: String?,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                    onClick = {}
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
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
