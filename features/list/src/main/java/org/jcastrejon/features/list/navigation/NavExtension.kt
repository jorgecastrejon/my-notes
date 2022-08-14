package org.jcastrejon.features.list.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.jcastrejon.features.list.ui.ListScreen
import org.jcastrejon.features.list.ui.ListViewModel

const val ListFeatureRoute = "List"

fun NavGraphBuilder.listRoute(
    onAddNoteClick: () -> Unit,
    onNoteClick: (Int) -> Unit,
) {
    composable(ListFeatureRoute) {
        val listViewModel: ListViewModel = hiltViewModel()

        ListScreen(
            listViewModel = listViewModel,
            onAddNoteClick = onAddNoteClick,
            onNoteClick = onNoteClick
        )
    }
}