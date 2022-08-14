package org.jcastrejon.features.detail.ui.arch

import org.jcastrejon.arch.mvi.MviState
import org.jcastrejon.notes.Note

data class DetailState(
    val note: Note? = null
) : MviState