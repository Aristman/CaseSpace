package ru.marslab.casespace.data.mapper

import ru.marslab.casespace.data.model.ApodNW
import ru.marslab.casespace.data.model.EpicNW
import ru.marslab.casespace.data.model.MarsPhotoNW
import ru.marslab.casespace.data.model.MarsRoverNW
import ru.marslab.casespace.domain.model.*
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

fun MarsPhotoNW.toDomain(): List<MarsImage> =
    this.photos.map {
        MarsImage(
            id = it.id,
            url = it.imgSrc,
            date = it.earthDate,
            sol = it.sol,
            camera = it.camera.fullName,
            rover = it.rover.toDomain()
        )
    }


fun MarsPhotoNW.Photo.Rover.toDomain(): MarsRover =
    MarsRover(
        id = id,
        landingDate = landingDate,
        launchDate = launchDate,
        name = name,
        status = status,
    )

fun MarsRoverNW.toDomain(): MarsRover =
    photoManifest.run {
        MarsRover(
            landingDate = landingDate,
            launchDate = launchDate,
            name = name,
            status = status,
            maxDate = maxDate,
            maxSol = maxSol,
            totalPhotos = totalPhotos
        )
    }
