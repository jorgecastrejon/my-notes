package org.jcastrejon.features.list.ui.arch

import org.jcastrejon.arch.mvi.MviResult
import org.jcastrejon.notes.Note

sealed class ListResult : MviResult {
    object AddNote : ListResult()
    object NotesBeingFetched : ListResult()
    object ToggleEditNode : ListResult()
    class NotesLoaded(val notes: List<Note>) : ListResult()
}
