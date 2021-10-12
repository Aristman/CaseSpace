package ru.marslab.casespace.domain.model

data class Note(
    val id: String,
    val title: String,
    val categoryId: String? = null,
    val categoryName: String? = null,
    val description: String
)
