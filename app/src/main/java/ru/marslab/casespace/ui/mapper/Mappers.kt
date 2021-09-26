package ru.marslab.casespace.ui.mapper

import android.os.Build
import ru.marslab.casespace.domain.model.EarthImage
import ru.marslab.casespace.domain.model.MarsImage
import ru.marslab.casespace.domain.model.MarsRover
import ru.marslab.casespace.ui.model.EarthUi
import ru.marslab.casespace.ui.model.MarsPhotoUi
import ru.marslab.casespace.ui.model.RoverUi
import java.time.LocalDate

fun EarthImage.toUi(): EarthUi =
    EarthUi(
        imageUrl = imageUrl,
        thumbUrl = thumbUrl,
        description = description
    )

fun MarsRover.toUi(): RoverUi {
    val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().toString()
    } else {
        ""
    }
    return RoverUi(name, landingDate, totalPhotos ?: 0, maxDate ?: date)
}

fun MarsImage.toUi(): MarsPhotoUi =
    MarsPhotoUi(url, camera)