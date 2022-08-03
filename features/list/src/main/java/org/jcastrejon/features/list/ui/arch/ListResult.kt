package org.jcastrejon.features.list.ui.arch

import org.jcastrejon.arch.mvi.MviResult

sealed class ListResult : MviResult {
    object AddNote : ListResult()
    object NotesBeingFetched : ListResult()
    class NotesLoaded(val notes: List<Any>) : ListResult()
}
