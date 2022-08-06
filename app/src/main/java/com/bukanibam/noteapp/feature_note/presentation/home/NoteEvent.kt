package com.bukanibam.noteapp.feature_note.presentation.home

import com.bukanibam.noteapp.feature_note.domain.model.Note
import com.bukanibam.noteapp.feature_note.domain.util.NoteOrder
import com.bukanibam.noteapp.feature_note.domain.util.OrderType

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val note: Note): NoteEvent()
    object RestoreDeletedNote: NoteEvent()
    object ToggleMenuItem: NoteEvent()
}
