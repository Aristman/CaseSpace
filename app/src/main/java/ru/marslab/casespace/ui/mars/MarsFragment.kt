package ru.marslab.casespace.ui.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentMarsBinding

class MarsFragment : Fragment() {
    private var _binding: FragmentMarsBinding? = null
    private val binding: FragmentMarsBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}