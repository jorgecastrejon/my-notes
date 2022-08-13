package org.jcastrejon.notes.usecase

import org.jcastrejon.notes.Note
import org.jcastrejon.notes.data.NotesRepository

class GetNoteById(
    private val repository: NotesRepository
) {
    operator fun invoke(id: Int): Note? =
        repository.getNote(id = id)
}