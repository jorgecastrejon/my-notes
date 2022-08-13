package org.jcastrejon.editor.ui.arch

import org.jcastrejon.arch.mvi.MviState

data class EditorState(
    val note: Any? = null
) : MviState