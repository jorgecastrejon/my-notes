package org.jcastrejon.editor.ui.arch

import org.jcastrejon.arch.mvi.MviResult
import org.jcastrejon.notes.Note

sealed class EditorResult : MviResult {
    object NoteSaved : EditorResult()
    object PrepareFields : EditorResult()
    object Cancel : EditorResult()
    class LoadNote(val note: Note) : EditorResult()
}
