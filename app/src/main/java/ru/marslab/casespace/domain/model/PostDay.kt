package ru.marslab.casespace.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class PostDay(val minusDay: Long) : Parcelable {
    TODAY(0),
    YESTERDAY(1),
    BEFORE_YESTERDAY(2)
}