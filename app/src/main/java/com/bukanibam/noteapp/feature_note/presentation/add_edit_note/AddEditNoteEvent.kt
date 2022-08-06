package com.bukanibam.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitleText(val text: String): AddEditNoteEvent()
    data class OnChangeFocusStateTitle(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredDescText(val text: String): AddEditNoteEvent()
    data class OnChangeFocusStateDesc(val focusState: FocusState): AddEditNoteEvent()
    data class ColorChange(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}