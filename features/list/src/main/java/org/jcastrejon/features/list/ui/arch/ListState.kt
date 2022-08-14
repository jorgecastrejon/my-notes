package org.jcastrejon.features.list.ui.arch

import org.jcastrejon.arch.mvi.MviState
import org.jcastrejon.notes.Note

data class ListState(
    val isLoading: Boolean = true,
    val notes: List<Note> = emptyList(),
    val editMode: Boolean = false,
    val selectedNotes: List<Int> = emptyList()
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