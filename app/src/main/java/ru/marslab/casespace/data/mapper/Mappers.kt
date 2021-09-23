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

fun EpicNW.toDomain(collectionType: String, imageType: String): EarthImage {
    val baseImageUrl = "${Constant.getEpicImageArchivePath()}$collectionType/" +
            this.date.split(' ').first().split('-').joinToString("/")
    return EarthImage(
        imageUrl = "$baseImageUrl/$imageType/$image.$imageType",
        thumbUrl = "$baseImageUrl/${Constant.THUMBS_IMAGE_TYPE}/$image.${Constant.JPG_IMAGE_TYPE}",
        description = caption,
        date = date
    )
}