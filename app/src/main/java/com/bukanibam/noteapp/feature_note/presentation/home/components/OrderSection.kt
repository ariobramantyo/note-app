package com.bukanibam.noteapp.feature_note.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bukanibam.noteapp.feature_note.domain.util.NoteOrder
import com.bukanibam.noteapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    order: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                title = "Title",
                checked = order is NoteOrder.Title,
                isChecked = { onOrderChange(NoteOrder.Title(order.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                title = "Date",
                checked = order is NoteOrder.Date,
                isChecked = { onOrderChange(NoteOrder.Date(order.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                title = "Color",
                checked = order is NoteOrder.Color,
                isChecked = { onOrderChange(NoteOrder.Color(order.orderType)) }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                title = "Ascending",
                checked = order.orderType is OrderType.Ascending,
                isChecked = { onOrderChange(order.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                title = "Descending",
                checked = order.orderType is OrderType.Descending,
                isChecked = { onOrderChange(order.copy(OrderType.Descending)) }
            )
        }
    }
}