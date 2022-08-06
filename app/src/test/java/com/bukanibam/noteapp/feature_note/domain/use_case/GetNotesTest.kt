package com.bukanibam.noteapp.feature_note.domain.use_case

import com.bukanibam.noteapp.feature_note.data.repository.FakeNoteRepository
import com.bukanibam.noteapp.feature_note.domain.model.Note
import com.bukanibam.noteapp.feature_note.domain.util.NoteOrder
import com.bukanibam.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetNotesTest {
    private lateinit var getNotes: GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            val note = Note(
                title = c.toString(),
                desc = c.toString(),
                timestamp = index.toLong(),
                color = index,
                id = index
            )
            notesToInsert.add(note)
        }
        notesToInsert.shuffle()
        notesToInsert.forEach { note ->
            runBlocking {
                fakeNoteRepository.insertNote(note)
            }
        }

        getNotes = GetNotes(fakeNoteRepository)
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].title < notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].title > notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].color < notes[i+1].color)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].color > notes[i+1].color)
        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].timestamp < notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].timestamp > notes[i+1].timestamp)
        }
    }

}