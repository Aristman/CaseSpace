package ru.marslab.casespace.domain.util

import android.accounts.NetworkErrorException
import android.app.AlertDialog
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.marslab.casespace.R
import ru.marslab.casespace.domain.repository.Constant
import java.io.IOException
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

fun Fragment.handleError(error: Throwable, repeatAction: () -> Unit) {
    when (error) {
        is NetworkErrorException -> {
            Snackbar.make(
                requireView(),
                error.message.toString(),
                Snackbar.LENGTH_LONG
            ).show()
        }
        is IOException -> {
            Snackbar.make(
                requireView(),
                Constant.NO_INTERNET_CONNECTION,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.repeat) {
                    repeatAction()
                }
                .show()
        }
        else -> {
            throw error
        }
    }
}