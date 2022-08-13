package org.jcastrejon.editor.ui

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
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
import org.jcastrejon.notes.Note
import org.jcastrejon.notes.NoteUpdate
import org.jcastrejon.notes.usecase.CreateNote
import org.jcastrejon.notes.usecase.GetNoteById
import org.jcastrejon.notes.usecase.UpdateNote
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNoteById: GetNoteById,
    private val createNote: CreateNote,
    private val updateNote: UpdateNote,
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
                is OnSaveClick -> saveNote(
                    note = action.note,
                    title = action.title,
                    body = action.body
                )
                is OnBackClick -> flowOf(Cancel)
            }
        }

    override fun reduce(previousState: EditorState, result: EditorResult): EditorState =
        when (result) {
            is LoadNote -> previousState.copy(note = result.note)
            is PrepareFields,
            is NoteSaved,
            is Cancel -> previousState
        }

    override fun onResult(result: EditorResult) {
        super.onResult(result)
        when (result) {
            is NoteSaved,
            is Cancel -> effects.trySend(GoBack)
            is LoadNote,
            is PrepareFields -> Unit
        }
    }

    private fun findNote(noteId: Int): Flow<EditorResult> = flow {
        val note = withContext(Dispatchers.IO) { getNoteById(noteId) }

        if (note != null) {
            emit(LoadNote(note = note))
        } else {
            emit(PrepareFields)
        }
    }

    private fun saveNote(note: Note?, title: String, body: String): Flow<EditorResult> = flow {
        if (note != null) {
            withContext(Dispatchers.IO) {
                updateNote(
                    id = note.id,
                    data = NoteUpdate(
                        title = title,
                        body = body,
                        createdDate = note.createdDate,
                        lastUpdated = LocalDate.now(),
                        color = note.color
                    )
                )
            }
        } else {
            createNote(
                data = NoteUpdate(
                    title = title,
                    body = body,
                    createdDate = LocalDate.now(),
                    lastUpdated = LocalDate.now(),
                    color = ColorPicker.random()
                )
            )
        }

        emit(NoteSaved)
    }
}