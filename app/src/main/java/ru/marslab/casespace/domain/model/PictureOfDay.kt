package ru.marslab.casespace.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureOfDay(
    val date: String,
    val title: String,
    val url: String,
    val hdUrl: String?,
    val type: MediaType,
    val description: String
) : Parcelable
