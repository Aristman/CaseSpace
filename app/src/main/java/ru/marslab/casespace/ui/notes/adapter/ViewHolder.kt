package ru.marslab.casespace.ui.notes.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.marslab.casespace.databinding.ItemCollapseNoteBinding
import ru.marslab.casespace.databinding.ItemExpandNoteBinding
import ru.marslab.casespace.databinding.ItemFooterNoteListBinding
import ru.marslab.casespace.databinding.ItemHeaderNoteListBinding
import ru.marslab.casespace.ui.model.NoteUi

abstract class BaseNoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: NoteUi)
}

class HeaderNoteListViewHolder(
    binding: ItemHeaderNoteListBinding,
) :
    BaseNoteViewHolder(binding.root) {
    override fun bind(item: NoteUi) {}
}

class FooterNoteListViewHolder(
    binding: ItemFooterNoteListBinding,
) :
    BaseNoteViewHolder(binding.root) {
    override fun bind(item: NoteUi) {}
}

class CollapseNoteItemViewHolder(
    private val binding: ItemCollapseNoteBinding,
    private val itemClickCallback: (item: NoteUi, position: Int) -> Unit
) :
    BaseNoteViewHolder(binding.root) {

    override fun bind(item: NoteUi) {
        binding.run {
            noteTitle.text = item.title
            noteDescription.text = item.description
            root.setOnClickListener {
                itemClickCallback(item, layoutPosition)
            }
        }
    }
}

class ExpandNoteItemViewHolder(
    private val binding: ItemExpandNoteBinding,
    private val itemClickCallback: (item: NoteUi, position: Int) -> Unit
) :
    BaseNoteViewHolder(binding.root) {

    override fun bind(item: NoteUi) {
        binding.run {
            noteTitle.text = item.title
            noteDescription.text = item.description
            root.setOnClickListener {
                itemClickCallback(item, layoutPosition)
            }
        }
    }
}