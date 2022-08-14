package org.jcastrejon.notes.usecase

import org.jcastrejon.notes.data.NotesRepository

class DeleteNote(
    private val repository: NotesRepository
) {
    operator fun invoke(id: Int) {
        repository.delete(id)
    }
}