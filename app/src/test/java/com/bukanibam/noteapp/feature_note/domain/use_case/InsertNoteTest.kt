package com.bukanibam.noteapp.feature_note.domain.use_case

import com.bukanibam.noteapp.feature_note.data.repository.FakeNoteRepository
import com.bukanibam.noteapp.feature_note.domain.model.InvalidNoteException
import com.bukanibam.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class InsertNoteTest {
    private lateinit var insertNote: InsertNote
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var notes: List<Note>

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        insertNote = InsertNote(fakeNoteRepository)
        runBlocking {
            notes = fakeNoteRepository.getNotes().first()
        }
    }

    @Test
    fun `Insert note with valid input`() = runBlocking {
        val note = Note(
            title = "test title",
            desc = "test description",
            timestamp = 1,
            color = 1,
            id = 1
        )
        fakeNoteRepository.insertNote(note)

        assertTrue(notes.contains(note))
    }

    @Test(expected = InvalidNoteException::class)
    fun `Insert note with empty title, throws exception`() = runBlocking {
        val note = Note(
            title = "",
            desc = "test description",
            timestamp = 1,
            color = 1,
            id = 1
        )
        insertNote(note)
    }

    @Test(expected = InvalidNoteException::class)
    fun `Insert note with empty description, throws exception`() = runBlocking {
        val note = Note(
            title = "test title",
            desc = "",
            timestamp = 1,
            color = 1,
            id = 1
        )
        insertNote(note)
    }

}
