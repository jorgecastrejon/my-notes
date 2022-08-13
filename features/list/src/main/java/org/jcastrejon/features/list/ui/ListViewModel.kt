package org.jcastrejon.features.list.ui

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.jcastrejon.arch.mvi.MviViewModel
import org.jcastrejon.features.list.ui.arch.ListAction
import org.jcastrejon.features.list.ui.arch.ListAction.EditNoteClick
import org.jcastrejon.features.list.ui.arch.ListAction.AddNoteClick
import org.jcastrejon.features.list.ui.arch.ListAction.LoadData
import org.jcastrejon.features.list.ui.arch.ListEffect
import org.jcastrejon.features.list.ui.arch.ListEffect.GoToAddNote
import org.jcastrejon.features.list.ui.arch.ListResult
import org.jcastrejon.features.list.ui.arch.ListResult.*
import org.jcastrejon.features.list.ui.arch.ListState
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : MviViewModel<ListAction, ListResult, ListState, ListEffect>(
    initialState = ListState()
) {
    override fun Flow<ListAction>.actionsToResultTransformer(): Flow<ListResult> =
        flatMapMerge { action ->
            when (action) {
                is LoadData -> fetchNotes()
                is AddNoteClick -> flowOf(AddNote)
                is EditNoteClick -> flowOf(ToggleEditNode)
            }
        }

    override fun reduce(previousState: ListState, result: ListResult): ListState =
        when (result) {
            is NotesBeingFetched -> previousState.copy(isLoading = true)
            is NotesLoaded -> previousState.copy(isLoading = false, notes = result.notes)
            is AddNote -> previousState
            is ToggleEditNode -> previousState.copy(editMode = !previousState.editMode)
        }

    override fun onResult(result: ListResult) {
        super.onResult(result)
        when (result) {
            is AddNote -> effects.trySend(GoToAddNote)
            is NotesBeingFetched,
            is NotesLoaded,
            is ToggleEditNode -> Unit
        }
    }

    private fun fetchNotes(): Flow<ListResult> = flow {
        emit(NotesBeingFetched)
//        val notes = withContext(Dispatchers.IO) { getNotes() }

        emit(NotesLoaded(notes = emptyList()))
    }
}