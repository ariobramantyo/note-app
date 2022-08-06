package com.bukanibam.noteapp.feature_note.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    isChecked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = checked,
            onClick = isChecked,
            colors = RadioButtonDefaults.colors(
                unselectedColor = Color.White
            )
        )
        Text(
            text = title,
            style = MaterialTheme.typography.body1
        )
    }
}