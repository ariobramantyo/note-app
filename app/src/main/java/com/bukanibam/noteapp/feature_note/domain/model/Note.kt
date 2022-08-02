package com.bukanibam.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bukanibam.noteapp.ui.theme.*

@Entity
data class Note(
    val title: String,
    val desc: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey
    val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}