package org.jcastrejon.notes.usecase

import org.jcastrejon.notes.NoteUpdate
import org.jcastrejon.notes.data.NotesRepository

class UpdateNote(
    private val repository: NotesRepository
) {
    operator fun invoke(id: Int, data: NoteUpdate) {
        repository.update(id, data)
    }
}