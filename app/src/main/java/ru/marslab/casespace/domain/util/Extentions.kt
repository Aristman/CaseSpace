package ru.marslab.casespace.domain.util

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.marslab.casespace.domain.repository.Constant
import java.text.SimpleDateFormat
import java.util.*

fun Date.getNasaFormatDate(): String {
    return SimpleDateFormat(Constant.DATE_FORMAT_STRING, Locale.getDefault()).format(this)
}

fun View.showSnackMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.showSnackMessage(messageId: Int) {
    Snackbar.make(this, this.context.getString(messageId), Snackbar.LENGTH_LONG).show()
}