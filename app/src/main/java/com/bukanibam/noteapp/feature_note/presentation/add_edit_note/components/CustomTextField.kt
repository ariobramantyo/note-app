package com.bukanibam.noteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomTextField(
    modifier: Modifier,
    text: String = "",
    hint: String,
    textStyle: TextStyle,
    onValueChange : (String) -> Unit,
    isHintVisible: Boolean = true,
    onFocusState: (FocusState) -> Unit,
    singleLine: Boolean
) {
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusState(it)
                }
        )
        if (isHintVisible) Text(
            text = hint,
            style = textStyle,
            color = Color.LightGray
        )
    }
}