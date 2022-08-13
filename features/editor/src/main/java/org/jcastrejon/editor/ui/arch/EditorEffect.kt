package org.jcastrejon.editor.ui.arch

import org.jcastrejon.arch.mvi.MviEffect

sealed class EditorEffect: MviEffect {
    object GoBack: EditorEffect()
}
