package ru.marslab.casespace.ui.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentEarthBinding

class EarthFragment : Fragment() {
    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}