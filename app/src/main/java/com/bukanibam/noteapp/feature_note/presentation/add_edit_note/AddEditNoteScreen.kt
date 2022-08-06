package com.bukanibam.noteapp.feature_note.presentation.add_edit_note

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bukanibam.noteapp.feature_note.domain.model.Note
import com.bukanibam.noteapp.feature_note.presentation.add_edit_note.components.CustomTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.colorState.value)
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UIEvent.SaveNote -> {
                    navController.navigateUp()
                }
                is AddEditNoteViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save note"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Note.noteColors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(
                                elevation = 15.dp,
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .background(Color(color.toArgb()))
                            .border(
                                width = 2.dp,
                                if (viewModel.colorState.value == color.toArgb()) Color.Black
                                else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(color.toArgb()),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ColorChange(color.toArgb()))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                text = viewModel.noteTitleState.value.text,
                hint = viewModel.noteTitleState.value.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitleText(it))
                },
                onFocusState = {
                    viewModel.onEvent(AddEditNoteEvent.OnChangeFocusStateTitle(it))
                },
                singleLine = true,
                isHintVisible = viewModel.noteTitleState.value.isHintVisible,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                text = viewModel.noteDescState.value.text,
                hint = viewModel.noteDescState.value.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredDescText(it))
                },
                onFocusState = {
                    viewModel.onEvent(AddEditNoteEvent.OnChangeFocusStateDesc(it))
                },
                singleLine = false,
                isHintVisible = viewModel.noteDescState.value.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight().padding(horizontal = 16.dp)
            )
        }
    }
}