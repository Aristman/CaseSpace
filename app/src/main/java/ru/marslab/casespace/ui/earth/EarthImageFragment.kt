package ru.marslab.casespace.ui.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentEarthImageBinding

class EarthImageFragment : Fragment() {
    companion object {
        private const val IMAGE_URL_TAG = "image_url_tag"

        fun getInstance(imageUrl: String): EarthImageFragment =
            EarthImageFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_URL_TAG, imageUrl)
                }
            }
    }

    private var _binding: FragmentEarthImageBinding? = null
    private val binding: FragmentEarthImageBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthImageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { binding.earthImage.load(it.getString(IMAGE_URL_TAG)) }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}