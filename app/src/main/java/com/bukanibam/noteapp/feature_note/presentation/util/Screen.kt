package com.bukanibam.noteapp.feature_note.presentation.util

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object AddEditNoteScreen: Screen("add_edit_note_screen")
}
