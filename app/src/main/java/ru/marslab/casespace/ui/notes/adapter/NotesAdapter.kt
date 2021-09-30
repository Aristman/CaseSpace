package ru.marslab.casespace.ui.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.marslab.casespace.databinding.ItemNoteBinding
import ru.marslab.casespace.ui.model.NoteUi

val diffCallback = object : DiffUtil.ItemCallback<NoteUi>() {
    override fun areItemsTheSame(oldItem: NoteUi, newItem: NoteUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteUi, newItem: NoteUi): Boolean =
        oldItem == newItem
}

class NotesAdapter(private val itemClickCallback: (item: NoteUi) -> Unit) :
    ListAdapter<NoteUi, NotesViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding, itemClickCallback)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NotesViewHolder(
    private val binding: ItemNoteBinding,
    private val itemClickCallback: (item: NoteUi) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NoteUi) {
        binding.run {
            noteTitle.text = item.title
            noteDescription.text = item.description
            root.setOnClickListener {
                itemClickCallback(item)
            }
        }
    }
}
