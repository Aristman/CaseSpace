package ru.marslab.casespace.data.mapper

import ru.marslab.casespace.data.model.ApodNW
import ru.marslab.casespace.data.model.EpicNW
import ru.marslab.casespace.domain.model.EarthImage
import ru.marslab.casespace.domain.model.MediaType
import ru.marslab.casespace.domain.model.PictureOfDay
import ru.marslab.casespace.domain.repository.Constant

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

fun EpicNW.toDomain(collectionType: String, imageType: String): EarthImage =
    EarthImage(
        url = "${Constant.getEpicImageArchivePath()}$collectionType/${
            this.date.split(' ').first().split('-').joinToString("/")
        }/$imageType/$image.$imageType",
        description = caption,
        date = date
    )
