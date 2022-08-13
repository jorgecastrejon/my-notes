package org.jcastrejon.editor.ui.arch

import org.jcastrejon.arch.mvi.MviState
import org.jcastrejon.notes.Note

data class EditorState(
    val note: Note? = null
) : MviState