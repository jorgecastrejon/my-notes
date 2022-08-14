package org.jcastrejon.features.detail.ui.arch

import org.jcastrejon.arch.mvi.MviResult
import org.jcastrejon.notes.Note

sealed class DetailResult : MviResult {
    object Cancel : DetailResult()
    object LoadError : DetailResult()
    object NoteDeleted : DetailResult()
    class EditNote(val id: Int) : DetailResult()
    class LoadNote(val note: Note) : DetailResult()
}
