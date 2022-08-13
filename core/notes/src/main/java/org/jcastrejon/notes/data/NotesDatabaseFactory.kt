package org.jcastrejon.notes.data

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import org.jcastrejon.notes.NotesDatabase
import orgjcastrejonnotes.Note_dto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object NotesDatabaseFactory {

    fun createDatabase(driver: SqlDriver): NotesDatabase =
        NotesDatabase(
            driver = driver,
            note_dtoAdapter = Note_dto.Adapter(
                created_dateAdapter = dateAdapter,
                last_updatedAdapter = dateAdapter,
            )
        )

    private val dateAdapter = object : ColumnAdapter<LocalDate, String> {
        override fun decode(databaseValue: String): LocalDate =
            LocalDate.parse(databaseValue, DateTimeFormatter.ISO_LOCAL_DATE)

        override fun encode(value: LocalDate): String =
            value.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}