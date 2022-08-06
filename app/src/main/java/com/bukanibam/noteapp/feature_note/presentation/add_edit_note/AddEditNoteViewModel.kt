package com.bukanibam.noteapp.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bukanibam.noteapp.feature_note.domain.model.InvalidNoteException
import com.bukanibam.noteapp.feature_note.domain.model.Note
import com.bukanibam.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitleState = mutableStateOf(
        NoteTextFieldState(
            hint = "Write title here..."
        )
    )
    val noteTitleState: State<NoteTextFieldState> = _noteTitleState

    private val _noteDescState = mutableStateOf(
        NoteTextFieldState(
            hint = "Write description here..."
        )
    )
    val noteDescState: State<NoteTextFieldState> = _noteDescState

    private val _colorState = mutableStateOf(Note.noteColors.random().toArgb())
    val colorState: State<Int> = _colorState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow: SharedFlow<UIEvent> = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object SaveNote : UIEvent()
    }

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            Log.d("NOTE ID", "note id = $noteId")
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteById(noteId)?.also { note ->
                        _colorState.value = note.color
                        _noteTitleState.value = noteTitleState.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteDescState.value = noteDescState.value.copy(
                            text = note.desc,
                            isHintVisible = false
                        )
                        currentNoteId = note.id
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitleText -> {
                _noteTitleState.value = noteTitleState.value.copy(
                    text = event.text
                )
            }
            is AddEditNoteEvent.OnChangeFocusStateTitle -> {
                _noteTitleState.value = noteTitleState.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitleState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredDescText -> {
                _noteDescState.value = noteDescState.value.copy(
                    text = event.text
                )
            }
            is AddEditNoteEvent.OnChangeFocusStateDesc -> {
                _noteDescState.value = noteDescState.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteDescState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ColorChange -> {
                _colorState.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.insertNote(
                            Note(
                                title = _noteTitleState.value.text,
                                desc = _noteDescState.value.text,
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId,
                                color = _colorState.value
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(UIEvent.ShowSnackbar("Couldn't save note. Make sure you fill note title and description"))
                    }
                }
            }
        }
    }
}