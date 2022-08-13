package org.jcastrejon.editor.ui.arch

import org.jcastrejon.arch.mvi.MviResult

sealed class EditorResult : MviResult {
    object CreateNote : EditorResult()
    object Cancel : EditorResult()
    class LoadNote(val note: Any) : EditorResult()
}
