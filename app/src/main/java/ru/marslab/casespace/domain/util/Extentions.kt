package ru.marslab.casespace.domain.util

import android.app.AlertDialog
import android.view.View
import ru.marslab.casespace.R
import ru.marslab.casespace.domain.repository.Constant
import java.text.SimpleDateFormat
import java.util.*

fun Date.getNasaFormatDate(): String {
    return SimpleDateFormat(Constant.DATE_FORMAT_STRING, Locale.getDefault()).format(this)
}

fun View.showMessage(message: String) {
    AlertDialog.Builder(this.context)
        .setMessage(message)
        .setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }.create()
        .show()
}

fun View.showMessage(messageId: Int) {
    AlertDialog.Builder(this.context)
        .setMessage(messageId)
        .setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }.create()
        .show()
}