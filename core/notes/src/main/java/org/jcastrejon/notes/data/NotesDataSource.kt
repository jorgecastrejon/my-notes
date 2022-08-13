package org.jcastrejon.notes.data

import org.jcastrejon.notes.Note
import org.jcastrejon.notes.NoteUpdate
import org.jcastrejon.notes.NotesDatabase
import orgjcastrejonnotes.Note_dto
import orgjcastrejonnotes.NotesQueries

interface NotesDataSource {

    fun create(data: NoteUpdate)
    fun update(id: Int, data: NoteUpdate)
    fun get(id: Int): Note?
    fun getAll(): List<Note>
    fun remove(id: Int)
    fun recover(id: Int)
}

class NotesSQLDataSource(
    private val database: NotesDatabase
) : NotesDataSource {

    private val queries: NotesQueries get() = database.notesQueries

    override fun create(data: NoteUpdate) {
        queries.insert(
            title = data.title,
            body = data.body,
            created_date = data.createdDate,
            last_updated = data.lastUpdated,
            color = data.color
        )
    }

    override fun update(id: Int, data: NoteUpdate) {
        queries.update(
            id = id,
            title = data.title,
            body = data.body,
            createdDate = data.createdDate,
            lastUpdated = data.lastUpdated,
            color = data.color
        )
    }

    override fun get(id: Int): Note? =
        queries.get(id).executeAsOneOrNull()?.toDomain()

    override fun getAll(): List<Note> =
        queries.getAll()
            .executeAsList()
            .filterNot(Note_dto::deleted)
            .map(Note_dto::toDomain)

    override fun remove(id: Int) {
        queries.remove(id = id)
    }

    override fun recover(id: Int) {
        queries.remove(id = id)
    }
}
