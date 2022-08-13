package org.jcastrejon.editor.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.jcastrejon.editor.ui.EditorScreen
import org.jcastrejon.editor.ui.EditorViewModel

private const val routePath = "Editor"
const val argument = "noteId"
const val argumentDefault = -1
val EditorFeatureRoute = { noteId: Int -> "$routePath/$noteId" }

fun NavGraphBuilder.editorRoute(
    onBack: () -> Unit,
) {
    composable(
        route = "$routePath/{$argument}",
        arguments = listOf(navArgument(argument) { type = NavType.IntType })
    ) {
        val editorViewModel: EditorViewModel = hiltViewModel()

        EditorScreen(
            editorViewModel = editorViewModel,
            onBack = onBack
        )
    }
}
