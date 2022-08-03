package org.jcastrejon.mynotes.ui

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.jcastrejon.mynotes.navigation.MyNotesRouter
import org.jcastrejon.mynotes.navigation.MyNotesScreen.List
import org.jcastrejon.theme.MyNotesTheme

@Composable
fun MyNotesApp() {
    MyNotesTheme {
        val navController = rememberNavController()
        MyNotesRouter(
            modifier = Modifier
                .background(MaterialTheme.colors.background),
            navController = navController,
            startDestination = List()
        )
    }
}