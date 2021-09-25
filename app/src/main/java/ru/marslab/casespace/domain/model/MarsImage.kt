package ru.marslab.casespace.domain.model


data class MarsImage(
    val id: Int,
    val url: String,
    val date: String,
    val sol: Int,
    val camera: String,
    val rover: MarsRover
)