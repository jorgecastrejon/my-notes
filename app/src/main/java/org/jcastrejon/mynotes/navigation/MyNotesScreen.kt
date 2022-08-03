package org.jcastrejon.mynotes.navigation

import org.jcastrejon.features.list.navigation.ListFeatureRoute

sealed class MyNotesScreen {
    object List : MyNotesScreen() {
        operator fun invoke(): String = ListFeatureRoute
    }
}
