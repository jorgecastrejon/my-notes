package org.jcastrejon.editor.ui

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun rememberFieldsState(
    from: Any? = null
): EditorFieldsState =
    rememberSaveable(saver = EditorFieldsState.Saver) {
        EditorFieldsState(initialTitle = String(), initialBody = String())
    }

@Stable
class EditorFieldsState(
    initialTitle: String,
    initialBody: String
) {

    var title by mutableStateOf(initialTitle)

    var body by mutableStateOf(initialBody)

    companion object {

        val Saver: Saver<EditorFieldsState, *> = listSaver(
            save = { item ->
                listOf(item.title, item.body)
            },
            restore = { list ->
                EditorFieldsState(initialTitle = list.first(), initialBody = list.last())
            }
        )
    }
}