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
import ru.marslab.casespace.ui.model.NoteUi

val diffCallback = object : DiffUtil.ItemCallback<NoteItem>() {
    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean =
        if (newItem is NoteItem.CollapseNote && oldItem is NoteItem.CollapseNote) {
            newItem.data.id == oldItem.data.id
        } else if (newItem is NoteItem.ExpandNote && oldItem is NoteItem.ExpandNote) {
            newItem.data.id == oldItem.data.id
        } else true


    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean =
        oldItem == newItem
}

class NotesAdapter(private val itemClickCallback: (item: NoteUi, position: Int) -> Unit) :
    ListAdapter<NoteItem, BaseNoteViewHolder>(diffCallback) {
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
        getItem(position).data?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is NoteItem.CollapseNote -> R.layout.item_collapse_note
            is NoteItem.ExpandNote -> R.layout.item_expand_note
            NoteItem.Footer -> R.layout.item_footer_note_list
            NoteItem.Header -> R.layout.item_header_note_list
        }
}
