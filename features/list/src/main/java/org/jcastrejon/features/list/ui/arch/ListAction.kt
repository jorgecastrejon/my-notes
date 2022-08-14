package org.jcastrejon.features.list.ui.arch

import org.jcastrejon.arch.mvi.MviAction

sealed class ListAction : MviAction {
    object LoadData : ListAction()
    object AddNoteClick : ListAction()
    object EditNoteClick : ListAction()
    class NoteClick(val id: Int, val editMode: Boolean) : ListAction()
    class DeleteClick(val ids: List<Int>) : ListAction()
}
