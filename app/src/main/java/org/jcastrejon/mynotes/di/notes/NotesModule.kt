package org.jcastrejon.mynotes.di.notes

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.jcastrejon.notes.NotesDatabase
import org.jcastrejon.notes.data.NotesDataSource
import org.jcastrejon.notes.data.NotesDatabaseFactory
import org.jcastrejon.notes.data.NotesRepository
import org.jcastrejon.notes.data.NotesSQLDataSource
import org.jcastrejon.notes.usecase.CreateNote
import org.jcastrejon.notes.usecase.GetNoteById
import org.jcastrejon.notes.usecase.GetNotes
import org.jcastrejon.notes.usecase.UpdateNote
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotesModule {

    @Provides
    fun provideCreateNote(
        repository: NotesRepository
    ): CreateNote =
        CreateNote(repository = repository)

    @Provides
    fun provideUpdateNote(
        repository: NotesRepository
    ): UpdateNote =
        UpdateNote(repository = repository)

    @Provides
    fun provideGetNoteById(
        repository: NotesRepository
    ): GetNoteById =
        GetNoteById(repository = repository)

    @Provides
    fun provideGetNotes(
        repository: NotesRepository
    ): GetNotes =
        GetNotes(repository = repository)

    @Singleton
    @Provides
    fun provideNotesRepository(
        dataSource: NotesDataSource
    ): NotesRepository =
        NotesRepository(
            dataSource = dataSource
        )

    @Singleton
    @Provides
    fun provideNotesDataSource(
        database: NotesDatabase
    ): NotesDataSource =
        NotesSQLDataSource(
            database = database,
        )

    @Singleton
    @Provides
    fun provideNotesDatabase(
        driver: SqlDriver
    ): NotesDatabase =
        NotesDatabaseFactory.createDatabase(driver = driver)

    @Singleton
    @Provides
    fun provideSqlDriver(
        @ApplicationContext applicationContext: Context
    ): SqlDriver =
        AndroidSqliteDriver(
            schema = NotesDatabase.Schema,
            context = applicationContext,
            name = "test.db"
        )
}
