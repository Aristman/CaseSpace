package ru.marslab.casespace.ui.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentApodBinding
import ru.marslab.casespace.ui.apod.adapter.ApodContentAdapter
import ru.marslab.casespace.ui.custom.BaseFragment

class ApodFragment : BaseFragment() {
    private var _binding: FragmentApodBinding? = null
    private val binding: FragmentApodBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    private lateinit var apodContentAdapter: ApodContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApodBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViewPage()
        setToolbarTitle(getString(R.string.picture_of_day))
    }


    private fun initViewPage() {
        apodContentAdapter = ApodContentAdapter(this)
        binding.vpPictures.adapter = apodContentAdapter
        TabLayoutMediator(binding.tabPictures, binding.vpPictures) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.today)
                1 -> getString(R.string.yesterday)
                2 -> getString(R.string.before_yesterday)
                else -> getString(R.string.today)
            }
        }.attach()
        binding.vpPictures.currentItem = 0
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}