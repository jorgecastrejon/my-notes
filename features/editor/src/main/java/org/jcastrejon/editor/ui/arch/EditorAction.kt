package org.jcastrejon.editor.ui.arch

import org.jcastrejon.arch.mvi.MviAction

sealed class EditorAction : MviAction {
    class GetNote(val noteId: Int) : EditorAction()
    object OnBackClick: EditorAction()
    object OnSaveClick: EditorAction()
}
