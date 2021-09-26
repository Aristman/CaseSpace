package ru.marslab.casespace.ui.mapper

import ru.marslab.casespace.domain.model.EarthImage
import ru.marslab.casespace.ui.model.EarthUi

fun EarthImage.toUi(): EarthUi =
    EarthUi(url = url, description = description)