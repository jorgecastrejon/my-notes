package org.jcastrejon.mynotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.jcastrejon.editor.navigation.editorRoute
import org.jcastrejon.features.list.navigation.listRoute
import org.jcastrejon.mynotes.navigation.MyNotesScreen.Editor
import org.jcastrejon.mynotes.navigation.MyNotesScreen.List

@Composable
fun MyNotesRouter(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = List()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        listRoute(onAddNoteClick = {
            navController.navigate(Editor()) {
                popUpTo(Editor()) { inclusive = true }
            }
        })

        editorRoute(onBack = {
            navController.popBackStack()
        })
    }
}
