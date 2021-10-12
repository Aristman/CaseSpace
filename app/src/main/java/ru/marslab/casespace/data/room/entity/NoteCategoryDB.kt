package ru.marslab.casespace.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class NoteCategoryDB(
    @PrimaryKey
    val id: String,
    val name: String
)
