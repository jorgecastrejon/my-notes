package org.jcastrejon.features.detail.ui

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.jcastrejon.arch.DispatcherProvider
import org.jcastrejon.arch.mvi.MviViewModel
import org.jcastrejon.features.detail.navigation.argument
import org.jcastrejon.features.detail.ui.arch.DetailAction
import org.jcastrejon.features.detail.ui.arch.DetailAction.*
import org.jcastrejon.features.detail.ui.arch.DetailEffect
import org.jcastrejon.features.detail.ui.arch.DetailEffect.GoBack
import org.jcastrejon.features.detail.ui.arch.DetailEffect.GoToEdit
import org.jcastrejon.features.detail.ui.arch.DetailResult
import org.jcastrejon.features.detail.ui.arch.DetailResult.*
import org.jcastrejon.features.detail.ui.arch.DetailState
import org.jcastrejon.notes.usecase.DeleteNote
import org.jcastrejon.notes.usecase.GetNoteById
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider,
    private val getNoteById: GetNoteById,
    private val deleteNote: DeleteNote
) : MviViewModel<DetailAction, DetailResult, DetailState, DetailEffect>(
    initialState = DetailState()
) {

    private val noteId: Int = savedStateHandle[argument] ?: -1

    override fun Flow<DetailAction>.actionsToResultTransformer(): Flow<DetailResult> =
        flatMapMerge { action ->
            when (action) {
                is LoadData -> findNote(noteId)
                OnBackClick -> flowOf(Cancel)
                OnEditClick -> flowOf(EditNote(noteId))
                OnRemoveClick -> removeNote(noteId)
            }
        }

    override fun reduce(previousState: DetailState, result: DetailResult): DetailState =
        when (result) {
            is LoadNote -> previousState.copy(note = result.note)
            is Cancel,
            is EditNote,
            is NoteDeleted,
            is LoadError -> previousState
        }

    override fun onResult(result: DetailResult) {
        super.onResult(result)
        when (result) {
            is EditNote -> effects.trySend(GoToEdit(result.id))
            is Cancel,
            is NoteDeleted,
            is LoadError -> effects.trySend(GoBack)
            is LoadNote -> Unit
        }
    }

    private fun findNote(noteId: Int): Flow<DetailResult> = flow {
        val note = withContext(dispatcherProvider.io) { getNoteById(noteId) }

        if (note != null) {
            emit(LoadNote(note = note))
        } else {
            emit(LoadError)
        }
    }

    private fun removeNote(noteId: Int): Flow<DetailResult> = flow {
        withContext(dispatcherProvider.io) { deleteNote(noteId) }

        emit(LoadError)
    }
}