package org.jcastrejon.notes.usecase

import org.jcastrejon.notes.NoteUpdate
import org.jcastrejon.notes.data.NotesRepository

class CreateNote(
    private val repository: NotesRepository
) {
    operator fun invoke(data: NoteUpdate) {
        repository.create(data)
    }
}