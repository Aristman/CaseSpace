package ru.marslab.casespace.domain.model

data class MarsRover(
    val id: Int = 0,
    val landingDate: String,
    val launchDate: String,
    val name: String,
    val status: String,
    val maxDate: String? = null,
    val maxSol: Int? = null,
    val totalPhotos: Int? = null
)