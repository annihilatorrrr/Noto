package com.noto.app.data.repository

import com.noto.app.domain.model.Library
import com.noto.app.domain.repository.LibraryRepository
import com.noto.app.domain.source.LocalLibraryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LibraryRepositoryImpl(private val dataSource: LocalLibraryDataSource) : LibraryRepository {

    override fun getLibraries(): Flow<List<Library>> = dataSource.getLibraries()

    override fun getLibraryById(libraryId: Long): Flow<Library> = dataSource.getLibrary(libraryId)

    override suspend fun createLibrary(library: Library) = withContext(Dispatchers.IO) {
        dataSource.createLibrary(library.copy(title = library.title))
    }

    override suspend fun updateLibrary(library: Library) = withContext(Dispatchers.IO) {
        dataSource.updateLibrary(library.copy(title = library.title))
    }

    override suspend fun deleteLibrary(library: Library) = withContext(Dispatchers.IO) {
        dataSource.deleteLibrary(library)
    }

    override suspend fun countLibraryNotes(libraryId: Long): Int = withContext(Dispatchers.IO) {
        dataSource.countLibraryNotes(libraryId)
    }
}