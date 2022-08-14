package org.jcastrejon.features.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import org.jcastrejon.arch.collectAsStateLifecycleAware
import org.jcastrejon.arch.rememberFlow
import org.jcastrejon.features.detail.ui.arch.DetailAction
import org.jcastrejon.features.detail.ui.arch.DetailAction.LoadData
import org.jcastrejon.features.detail.ui.arch.DetailAction.OnRemoveClick
import org.jcastrejon.features.detail.ui.arch.DetailAction.OnBackClick
import org.jcastrejon.features.detail.ui.arch.DetailAction.OnEditClick
import org.jcastrejon.features.detail.ui.arch.DetailEffect
import org.jcastrejon.features.detail.ui.arch.DetailEffect.GoBack
import org.jcastrejon.features.detail.ui.arch.DetailEffect.GoToEdit
import org.jcastrejon.features.detail.ui.arch.DetailState
import org.jcastrejon.features.detail.ui.components.DetailToolbar

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = viewModel(),
    onEditNote: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state: DetailState by detailViewModel.state.collectAsStateLifecycleAware(DetailState())
    val effects: Flow<DetailEffect> = rememberFlow(detailViewModel.viewEffects)

    LaunchedEffect(key1 = true) {
        detailViewModel.onAction(LoadData)
    }

    LaunchedEffect(true) {
        effects.collect { effect ->
            when (effect) {
                is GoBack -> onBack()
                is GoToEdit -> onEditNote(effect.noteId)
            }
        }
    }

    DetailContent(
        modifier = modifier,
        state = state,
        onAction = detailViewModel::onAction
    )
}

@Composable
fun DetailContent(
    modifier: Modifier,
    state: DetailState,
    onAction: (DetailAction) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .imePadding()
    ){
        DetailToolbar(
            onBackClick = { onAction(OnBackClick) },
            onEditClick = { onAction(OnEditClick) },
            onRemoveClick = { onAction(OnRemoveClick) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = state.note?.title.orEmpty(),
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = state.note?.createdDate?.toReadableFormat(showYear = true).orEmpty(),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = state.note?.body.orEmpty(),
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Light),
            color = MaterialTheme.colors.onBackground
        )
    }
}