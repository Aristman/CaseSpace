package ru.marslab.casespace.data.mapper

import ru.marslab.casespace.data.model.ApodNW
import ru.marslab.casespace.domain.model.MediaType
import ru.marslab.casespace.domain.model.PictureOfDay

fun ApodNW.toDomain(): PictureOfDay =
    PictureOfDay(
        date = date,
        title = title,
        url = url,
        hdUrl = hdUrl,
        type = if (mediaType == "video")
            MediaType.VIDEO
        else
            MediaType.PHOTO,
        description = explanation
    )