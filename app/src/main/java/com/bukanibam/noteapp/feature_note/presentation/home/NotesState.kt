package com.bukanibam.noteapp.feature_note.presentation.home

import com.bukanibam.noteapp.feature_note.domain.model.Note
import com.bukanibam.noteapp.feature_note.domain.util.NoteOrder
import com.bukanibam.noteapp.feature_note.domain.util.OrderType

data class NotesState (
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isMenuItemVisible: Boolean = false
)
