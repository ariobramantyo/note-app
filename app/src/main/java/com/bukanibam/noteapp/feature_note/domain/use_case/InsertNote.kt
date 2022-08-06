package com.bukanibam.noteapp.feature_note.domain.use_case

import com.bukanibam.noteapp.feature_note.domain.model.InvalidNoteException
import com.bukanibam.noteapp.feature_note.domain.model.Note
import com.bukanibam.noteapp.feature_note.domain.repository.NoteRepository

class InsertNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("the title of the note can't be empty")
        }
        if (note.desc.isBlank()) {
            throw InvalidNoteException("the description of the note can't be empty")
        }

        return repository.insertNote(note)
    }
}