package org.jcastrejon.notes.usecase

import org.jcastrejon.notes.Note
import org.jcastrejon.notes.data.NotesRepository

class GetNotes(
    private val repository: NotesRepository
) {
    operator fun invoke(): List<Note> =
        repository.getAll()
}