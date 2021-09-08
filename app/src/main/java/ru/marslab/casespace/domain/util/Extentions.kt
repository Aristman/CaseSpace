package ru.marslab.casespace.domain.util

import ru.marslab.casespace.domain.repository.Constant
import java.text.SimpleDateFormat
import java.util.*

fun Date.getNasaFormatDate(): String {
    val calendarDate = Calendar.getInstance().apply { time = this@getNasaFormatDate }
    return SimpleDateFormat(Constant.DATE_FORMAT_STRING, Locale.getDefault()).format(calendarDate)
}