package org.jcastrejon.editor.ui

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.jcastrejon.arch.mvi.MviViewModel
import org.jcastrejon.editor.navigation.argument
import org.jcastrejon.editor.navigation.argumentDefault
import org.jcastrejon.editor.ui.arch.EditorAction
import org.jcastrejon.editor.ui.arch.EditorAction.*
import org.jcastrejon.editor.ui.arch.EditorEffect
import org.jcastrejon.editor.ui.arch.EditorEffect.GoBack
import org.jcastrejon.editor.ui.arch.EditorResult
import org.jcastrejon.editor.ui.arch.EditorResult.*
import org.jcastrejon.editor.ui.arch.EditorState
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : MviViewModel<EditorAction, EditorResult, EditorState, EditorEffect>(
    initialState = EditorState()
) {

    private val noteId: Int = savedStateHandle[argument] ?: argumentDefault

    init {
        onAction(GetNote(noteId))
    }

    override fun Flow<EditorAction>.actionsToResultTransformer(): Flow<EditorResult> =
        flatMapMerge { action ->
            when (action) {
                is GetNote -> findNote(action.noteId)
                is OnBackClick -> flowOf(Cancel)
                is OnSaveClick -> flowOf(Cancel)
            }
        }

    override fun reduce(previousState: EditorState, result: EditorResult): EditorState =
        when (result) {
            is LoadNote -> previousState.copy(note = result.note)
            is CreateNote -> previousState
            is Cancel -> previousState
        }

    override fun onResult(result: EditorResult) {
        super.onResult(result)
        when (result) {
            is Cancel -> effects.trySend(GoBack)
            is LoadNote,
            is CreateNote -> Unit
        }
    }

    private fun findNote(noteId: Int): Flow<EditorResult> = flow {
//        val note = withContext(Dispatchers.IO) { getNote(noteId) }
        val note: Any? = null

        if (note != null) {
            emit(LoadNote(note = note))
        } else {
            emit(CreateNote)
        }
    }
}