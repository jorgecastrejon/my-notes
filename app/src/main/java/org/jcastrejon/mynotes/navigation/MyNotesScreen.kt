package org.jcastrejon.mynotes.navigation

import org.jcastrejon.editor.navigation.EditorFeatureRoute
import org.jcastrejon.editor.navigation.argumentDefault
import org.jcastrejon.features.list.navigation.ListFeatureRoute

sealed class MyNotesScreen {
    object List : MyNotesScreen() {
        operator fun invoke(): String = ListFeatureRoute
    }
    object Editor : MyNotesScreen() {
        operator fun invoke(id: Int = argumentDefault): String = EditorFeatureRoute(id)
    }
}
