package ru.marslab.casespace.ui.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentApodBinding
import ru.marslab.casespace.ui.util.ViewState


class ApodFragment : Fragment() {
    private var _binding: FragmentApodBinding? = null
    private val binding: FragmentApodBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    val apodViewModel by viewModels<ApodViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApodBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        apodViewModel.getImageOfDay()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            apodViewModel.imageOfDayPath.collect { result ->
                when (result) {
                    is ViewState.LoadError -> TODO()
                    ViewState.Loading -> TODO()
                    is ViewState.Successful<*> -> TODO()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}