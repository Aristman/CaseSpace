package ru.marslab.casespace.ui.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentApodBinding
import ru.marslab.casespace.ui.apod.adapter.ApodContentAdapter

class ApodFragment : Fragment() {
    private var _binding: FragmentApodBinding? = null
    private val binding: FragmentApodBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding) }

    private val apodContentAdapter: ApodContentAdapter by lazy { ApodContentAdapter(this) }

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
        binding.vpPictures.run {
            adapter = apodContentAdapter
            currentItem = 2
        }
        TabLayoutMediator(binding.tabPictures, binding.vpPictures) { tab, position ->
            var toolbarTitle = ""
            when (position) {
                0 -> toolbarTitle = getString(R.string.before_yesterday)
                1 -> toolbarTitle = getString(R.string.yesterday)
                2 -> toolbarTitle = getString(R.string.today)
            }
            tab.text = toolbarTitle
        }.attach()
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.picture_of_day)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}