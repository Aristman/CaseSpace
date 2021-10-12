package ru.marslab.casespace.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteUi(
    val id: String,
    val title: String,
    val category: String,
    val description: String
) : Parcelable
