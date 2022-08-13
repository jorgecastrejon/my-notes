package org.jcastrejon.features.list.ui.arch

import org.jcastrejon.arch.mvi.MviState

data class ListState(
    val isLoading: Boolean = true,
    val notes: List<Any> = emptyList(),
    val editMode: Boolean = false
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