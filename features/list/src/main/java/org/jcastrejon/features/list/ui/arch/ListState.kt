package org.jcastrejon.features.list.ui.arch

import org.jcastrejon.arch.mvi.MviState

data class ListState(
    val isLoading: Boolean = true,
    val filter: String = String(),
    val notes: List<Any> = emptyList()
) : MviState

enum class ViewState {
    Loading, Empty, List
}

val ListState.viewState: ViewState
    get() = when {
        isLoading -> ViewState.Loading
        notes.isEmpty() -> ViewState.Empty
        else -> ViewState.List
    }