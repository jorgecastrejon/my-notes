package org.jcastrejon.features.list.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.jcastrejon.arch.DispatcherProvider
import org.jcastrejon.arch.mvi.MviViewModel
import org.jcastrejon.features.list.ui.arch.ListAction
import org.jcastrejon.features.list.ui.arch.ListAction.*
import org.jcastrejon.features.list.ui.arch.ListEffect
import org.jcastrejon.features.list.ui.arch.ListEffect.GoToDetail
import org.jcastrejon.features.list.ui.arch.ListEffect.GoToAddNote
import org.jcastrejon.features.list.ui.arch.ListResult
import org.jcastrejon.features.list.ui.arch.ListResult.*
import org.jcastrejon.features.list.ui.arch.ListState
import org.jcastrejon.notes.usecase.DeleteNote
import org.jcastrejon.notes.usecase.GetNotes
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getNotes: GetNotes,
    private val deleteNote: DeleteNote,
) : MviViewModel<ListAction, ListResult, ListState, ListEffect>(
    initialState = ListState()
) {
    override fun Flow<ListAction>.actionsToResultTransformer(): Flow<ListResult> =
        flatMapMerge { action ->
            when (action) {
                is LoadData -> fetchNotes()
                is AddNoteClick -> flowOf(AddNote)
                is EditNoteClick -> flowOf(ToggleEditNode)
                is NoteClick -> toggleNoteState(id = action.id, editMode = action.editMode)
                is DeleteClick -> deleteNotes(ids = action.ids)
            }
        }

    override fun reduce(previousState: ListState, result: ListResult): ListState =
        when (result) {
            is NotesBeingFetched -> previousState.copy(isLoading = true)
            is NotesLoaded -> previousState.copy(
                isLoading = false,
                notes = result.notes,
                editMode = false,
                selectedNotes = emptyList()
            )
            is InspectNote,
            is AddNote -> previousState
            is ToggleEditNode -> previousState.copy(
                editMode = !previousState.editMode,
                selectedNotes = emptyList()
            )
            is ToggleNote -> previousState.toggleSelectedNote(id = result.id)
        }

    override fun onResult(result: ListResult) {
        super.onResult(result)
        when (result) {
            is InspectNote -> effects.trySend(GoToDetail(result.id))
            is AddNote -> effects.trySend(GoToAddNote)
            is NotesBeingFetched,
            is NotesLoaded,
            is ToggleNote,
            is ToggleEditNode -> Unit
        }
    }

    private fun fetchNotes(): Flow<ListResult> = flow {
        emit(NotesBeingFetched)
        val notes = withContext(dispatcherProvider.io) { getNotes() }

        emit(NotesLoaded(notes = notes))
    }

    private fun toggleNoteState(id: Int, editMode: Boolean): Flow<ListResult> = flow {
        if (editMode) {
            emit(ToggleNote(id = id))
        } else {
            emit(InspectNote(id = id))
        }
    }

    private fun deleteNotes(ids: List<Int>): Flow<ListResult> = flow {
        coroutineScope {
            ids.map { id -> async(dispatcherProvider.io) { deleteNote(id) } }.awaitAll()
        }

        emit(NotesBeingFetched)
        val notes = withContext(dispatcherProvider.io) { getNotes() }
        emit(NotesLoaded(notes = notes))

    }

    private fun ListState.toggleSelectedNote(id: Int): ListState =
        if (selectedNotes.contains(id)) {
            copy(selectedNotes = selectedNotes - id)
        } else {
            copy(selectedNotes = selectedNotes + id)
        }
}