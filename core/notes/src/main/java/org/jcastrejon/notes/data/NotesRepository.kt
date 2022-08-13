package org.jcastrejon.notes.data

import org.jcastrejon.notes.Note
import org.jcastrejon.notes.NoteUpdate

class NotesRepository(
    private val dataSource: NotesDataSource,
) {
    fun getNote(id: Int): Note? =
        dataSource.get(id)

    fun update(id: Int, data: NoteUpdate) {
        dataSource.update(id, data)
    }

    fun create(data: NoteUpdate) {
        dataSource.create(data)
    }
}
