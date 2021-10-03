package ru.marslab.casespace.ui.notes.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.marslab.casespace.databinding.ItemCollapseNoteBinding
import ru.marslab.casespace.databinding.ItemExpandNoteBinding
import ru.marslab.casespace.databinding.ItemFooterNoteListBinding
import ru.marslab.casespace.databinding.ItemHeaderNoteListBinding

abstract class BaseNoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: NoteListItem)
}

class HeaderNoteListViewHolder(
    binding: ItemHeaderNoteListBinding,
) :
    BaseNoteViewHolder(binding.root) {
    override fun bind(item: NoteListItem) {}
}

class FooterNoteListViewHolder(
    binding: ItemFooterNoteListBinding,
) :
    BaseNoteViewHolder(binding.root) {
    override fun bind(item: NoteListItem) {}
}

class CollapseNoteItemViewHolder(
    private val binding: ItemCollapseNoteBinding,
    private val itemClickCallback: (item: NoteListItem) -> Unit
) :
    BaseNoteViewHolder(binding.root) {

    override fun bind(item: NoteListItem) {
        (item as? NoteListItem.Note)?.data?.let {
            binding.run {
                noteTitle.text = it.title
                noteDescription.text = it.description
                root.setOnClickListener {
                    itemClickCallback(item)
                }
            }
        }
    }
}

class ExpandNoteItemViewHolder(
    private val binding: ItemExpandNoteBinding,
    private val itemClickCallback: (item: NoteListItem) -> Unit
) :
    BaseNoteViewHolder(binding.root) {

    override fun bind(item: NoteListItem) {
        (item as? NoteListItem.Note)?.data?.let {
            binding.run {
                noteTitle.text = it.title
                noteDescription.text = it.description
                root.setOnClickListener {
                    itemClickCallback(item)
                }
            }
        }
    }
}