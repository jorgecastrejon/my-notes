package org.jcastrejon.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import org.jcastrejon.arch.collectAsStateLifecycleAware
import org.jcastrejon.arch.rememberFlow
import org.jcastrejon.editor.R
import org.jcastrejon.editor.ui.arch.EditorAction
import org.jcastrejon.editor.ui.arch.EditorAction.OnBackClick
import org.jcastrejon.editor.ui.arch.EditorAction.OnSaveClick
import org.jcastrejon.editor.ui.arch.EditorEffect
import org.jcastrejon.editor.ui.arch.EditorEffect.GoBack
import org.jcastrejon.editor.ui.arch.EditorState
import org.jcastrejon.editor.ui.components.EditorToolbar

@Composable
fun EditorScreen(
    editorViewModel: EditorViewModel = viewModel(),
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state: EditorState by editorViewModel.state.collectAsStateLifecycleAware(EditorState())
    val effects: Flow<EditorEffect> = rememberFlow(editorViewModel.viewEffects)

    LaunchedEffect(true) {
        effects.collect { effect ->
            when (effect) {
                is GoBack -> onBack()
            }
        }
    }

    EditorContent(
        modifier = modifier,
        state = state,
        onAction = editorViewModel::onAction
    )
}

@Composable
fun EditorContent(
    modifier: Modifier,
    state: EditorState,
    onAction: (EditorAction) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val fieldsState = rememberFieldsState(from = state.note)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .imePadding()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
    ) {
        EditorToolbar(
            onSaveClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onAction(
                    OnSaveClick(
                        note = state.note,
                        title = fieldsState.title,
                        body = fieldsState.body
                    )
                )
            },
            onBackClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onAction(OnBackClick)
            },
        )
        Spacer(modifier = Modifier.height(24.dp))

        val selectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colors.onBackground,
            backgroundColor = MaterialTheme.colors.onBackground.copy(0.4f)
        )

        CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = fieldsState.title,
                onValueChange = { text -> fieldsState.title = text },
                textStyle = MaterialTheme.typography.h6,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colors.onBackground,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.background,
                    textColor = MaterialTheme.colors.onBackground,
                    placeholderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.editor_title_placeholder),
                        style = MaterialTheme.typography.h6
                    )
                }
            )
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = fieldsState.body,
                onValueChange = { text -> fieldsState.body = text },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colors.onBackground,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.background,
                    textColor = MaterialTheme.colors.onBackground,
                    placeholderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.editor_body_placeholder),
                        style = MaterialTheme.typography.body1
                    )
                }
            )
        }
    }
}
