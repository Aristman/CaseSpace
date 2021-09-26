package ru.marslab.casespace.ui.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentMarsImageBinding

class MarsImageFragment : Fragment() {

    private var _binding: FragmentMarsImageBinding? = null
    private val binding: FragmentMarsImageBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    private val args by navArgs<MarsImageFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarsImageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
    }

    private fun initListeners() {
        binding.marsBigImage.setOnClickListener {
            binding.root.run {
                if (targetPosition == 0f) {
                    transitionToEnd()
                } else {
                    transitionToStart()
                }
            }
        }
    }

    private fun initView() {
        binding.marsBigImage.load(args.imageUrl)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}