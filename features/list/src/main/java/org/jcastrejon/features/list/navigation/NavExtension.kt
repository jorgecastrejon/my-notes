package org.jcastrejon.features.list.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ListFeatureRoute = "List"

fun NavGraphBuilder.listRoute(
    onAddNoteClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable(ListFeatureRoute) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        )
    }
}