package ru.marslab.casespace.ui.mars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.marslab.casespace.databinding.ItemMarsPhotoBinding
import ru.marslab.casespace.ui.model.MarsPhotoUi

val marsPhotoDiffCallback = object : DiffUtil.ItemCallback<MarsPhotoUi>() {
    override fun areItemsTheSame(oldItem: MarsPhotoUi, newItem: MarsPhotoUi): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: MarsPhotoUi, newItem: MarsPhotoUi): Boolean =
        oldItem == newItem
}

class MarsPhotoAdapter(private val clickCallback: (item: MarsPhotoUi) -> Unit) :
    ListAdapter<MarsPhotoUi, MarsPhotoViewHolder>(marsPhotoDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
        val binding =
            ItemMarsPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarsPhotoViewHolder(binding, clickCallback)
    }

    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class MarsPhotoViewHolder(
    private val binding: ItemMarsPhotoBinding,
    private val clickCallback: (item: MarsPhotoUi) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MarsPhotoUi) {
        binding.marsPhoto.load(item.url)
        binding.marsPhotoCamera.text = item.camera
        binding.marsPhoto.setOnClickListener {
            clickCallback(item)
        }
    }
}
