package ru.marslab.casespace.ui.earth.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.marslab.casespace.ui.earth.EarthImageFragment
import ru.marslab.casespace.ui.model.EarthUi

class EarthImageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private var imageList: List<EarthUi> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setImageList(imageList: List<EarthUi>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }

    fun getItemThumbUrl(position: Int): String = imageList[position].thumbUrl

    override fun getItemCount(): Int = imageList.size

    override fun createFragment(position: Int): Fragment =
        if (position < imageList.size) {
            EarthImageFragment.getInstance(imageUrl = imageList[position].imageUrl)
        } else {
            EarthImageFragment()
        }

}