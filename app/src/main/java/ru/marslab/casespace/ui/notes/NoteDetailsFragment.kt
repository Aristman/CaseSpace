package ru.marslab.casespace.ui.notes

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import ru.marslab.casespace.R

class NoteDetailsFragment : DialogFragment() {
    companion object {
        const val FRAGMENT_TAG = "NoteDetailsFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setView(R.layout.fragment_note_details)
            .setPositiveButton(R.string.save) { dialog, _ ->
                saveNewNote()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()

    private fun saveNewNote() {
        val noteTitle = dialog?.findViewById<TextInputEditText>(R.id.ti_new_note_title)
        val noteText = dialog?.findViewById<TextInputEditText>(R.id.ti_new_note_text)
        Toast.makeText(requireContext(), noteText?.text, Toast.LENGTH_SHORT).show()
    }
}