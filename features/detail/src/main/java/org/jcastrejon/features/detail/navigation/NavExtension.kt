package org.jcastrejon.features.detail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.jcastrejon.features.detail.ui.DetailScreen
import org.jcastrejon.features.detail.ui.DetailViewModel

private const val routePath = "Detail"
const val argument = "noteId"
val DetailFeatureRoute = { noteId: Int -> "$routePath/$noteId" }

fun NavGraphBuilder.detailRoute(
    onEditNote: (Int) -> Unit,
    onBack: () -> Unit
) {
    composable(
        route = "$routePath/{$argument}",
        arguments = listOf(navArgument(argument) { type = NavType.IntType })
    ) {
        val detailViewModel: DetailViewModel = hiltViewModel()

        DetailScreen(
            detailViewModel = detailViewModel,
            onEditNote = onEditNote,
            onBack = onBack
        )
    }
}
