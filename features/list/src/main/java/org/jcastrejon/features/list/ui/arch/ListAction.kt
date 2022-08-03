package org.jcastrejon.features.list.ui.arch

import org.jcastrejon.arch.mvi.MviAction

sealed class ListAction : MviAction {
    object LoadData : ListAction()
    object AddNoteClick : ListAction()
}
