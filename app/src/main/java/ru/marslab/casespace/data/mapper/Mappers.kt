package ru.marslab.casespace.data.mapper

import ru.marslab.casespace.data.model.ApodNW
import ru.marslab.casespace.domain.model.Picture

fun ApodNW.toDomain(): Picture =
    Picture(
        date = date,
        title = title,
        url = url,
        description = explanation
    )