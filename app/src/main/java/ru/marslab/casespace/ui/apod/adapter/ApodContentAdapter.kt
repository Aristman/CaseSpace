package ru.marslab.casespace.ui.apod.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.marslab.casespace.domain.model.PostDay
import ru.marslab.casespace.ui.apod.ApodContentFragment


class ApodContentAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val apodContentFragment = ApodContentFragment()
        apodContentFragment.arguments =
            Bundle().apply {
                putParcelable(
                    ApodContentFragment.POST_DAY_TAG,
                    when (position) {
                        0 -> PostDay.TODAY
                        1 -> PostDay.YESTERDAY
                        2 -> PostDay.BEFORE_YESTERDAY
                        else -> PostDay.TODAY
                    }
                )
            }
        return apodContentFragment
    }

}