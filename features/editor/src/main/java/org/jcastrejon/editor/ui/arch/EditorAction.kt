package org.jcastrejon.editor.ui.arch

import org.jcastrejon.arch.mvi.MviAction
import org.jcastrejon.notes.Note

sealed class EditorAction : MviAction {
    class GetNote(val noteId: Int) : EditorAction()
    object OnBackClick : EditorAction()
    class OnSaveClick(val note: Note?, val title: String, val body: String) : EditorAction()
}
