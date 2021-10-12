package ru.marslab.casespace.ui.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.ItemCollapseNoteBinding
import ru.marslab.casespace.databinding.ItemExpandNoteBinding
import ru.marslab.casespace.databinding.ItemFooterNoteListBinding
import ru.marslab.casespace.databinding.ItemHeaderNoteListBinding

val diffCallback = object : DiffUtil.ItemCallback<NoteListItem>() {
    override fun areItemsTheSame(oldItem: NoteListItem, newItem: NoteListItem): Boolean =
        if (newItem is NoteListItem.Note && oldItem is NoteListItem.Note) {
            newItem.data.id == oldItem.data.id
        } else true


    override fun areContentsTheSame(oldItem: NoteListItem, newItem: NoteListItem): Boolean =
        oldItem == newItem
}

class NotesAdapter(private val itemClickCallback: (item: NoteListItem) -> Unit) :
    ListAdapter<NoteListItem, BaseNoteViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseNoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_header_note_list ->
                HeaderNoteListViewHolder(ItemHeaderNoteListBinding.bind(view))
            R.layout.item_footer_note_list ->
                FooterNoteListViewHolder(ItemFooterNoteListBinding.bind(view))
            R.layout.item_expand_note ->
                ExpandNoteItemViewHolder(ItemExpandNoteBinding.bind(view), itemClickCallback)
            R.layout.item_collapse_note ->
                CollapseNoteItemViewHolder(ItemCollapseNoteBinding.bind(view), itemClickCallback)
            else -> throw ExceptionInInitializerError()
        }
    }

    override fun onBindViewHolder(holder: BaseNoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int =
        when (val item = getItem(position)) {
            is NoteListItem.Note -> {
                if (item.isExpand) {
                    R.layout.item_expand_note
                } else {
                    R.layout.item_collapse_note
                }
            }
            NoteListItem.Footer -> R.layout.item_footer_note_list
            NoteListItem.Header -> R.layout.item_header_note_list
        }
}
