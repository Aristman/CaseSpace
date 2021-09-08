package ru.marslab.casespace.domain.util

import ru.marslab.casespace.domain.repository.Constant
import java.text.SimpleDateFormat
import java.util.*

fun Date.getNasaFormatDate(): String {
    return SimpleDateFormat(Constant.DATE_FORMAT_STRING, Locale.getDefault()).format(this).dropLast(1)
}