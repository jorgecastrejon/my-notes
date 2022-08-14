package org.jcastrejon.features.detail.ui.arch

import org.jcastrejon.arch.mvi.MviEffect

sealed class DetailEffect : MviEffect {
    object GoBack : DetailEffect()
    class GoToEdit(val noteId: Int) : DetailEffect()
}
