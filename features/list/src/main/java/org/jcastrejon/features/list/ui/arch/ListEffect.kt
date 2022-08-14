package org.jcastrejon.features.list.ui.arch

import org.jcastrejon.arch.mvi.MviEffect

sealed class ListEffect: MviEffect {
    object GoToAddNote: ListEffect()
    class GoToDetail(val id: Int): ListEffect()
}
