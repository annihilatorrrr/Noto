package com.noto.app.domain.repository

import com.noto.app.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotesByLibraryId(libraryId: Long): Flow<List<Note>>

    fun getArchivedNotesByLibraryId(libraryId: Long): Flow<List<Note>>

    fun getNoteById(noteId: Long): Flow<Note>

    suspend fun createNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun countLibraryNotes(libraryId: Long): Int
}