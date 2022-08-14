package org.jcastrejon.features.detail.ui.arch

import org.jcastrejon.arch.mvi.MviAction
import org.jcastrejon.notes.Note

sealed class DetailAction : MviAction {
    object LoadData : DetailAction()
    object OnBackClick : DetailAction()
    object OnEditClick : DetailAction()
    object OnRemoveClick : DetailAction()
}
