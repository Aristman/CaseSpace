package ru.marslab.casespace.ui.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentMarsBinding
import ru.marslab.casespace.domain.util.handleError
import ru.marslab.casespace.ui.mars.adapter.MarsPhotoAdapter
import ru.marslab.casespace.ui.model.MarsPhotoUi
import ru.marslab.casespace.ui.model.RoverUi
import ru.marslab.casespace.ui.util.ViewState

@AndroidEntryPoint
class MarsFragment : Fragment() {
    private var marsPhotoAdapter: MarsPhotoAdapter? = null
    private var _binding: FragmentMarsBinding? = null
    private val binding: FragmentMarsBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    private val marsViewModel by viewModels<MarsViewModel>()
    private var isExpandAppbar = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        marsPhotoAdapter?.let {
            binding.rvMarsPhotos.adapter = it
            binding.marsAppbar.setExpanded(isExpandAppbar)
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            marsViewModel.roverInfo.collect { result ->
                when (result) {
                    ViewState.Init -> {
                        binding.marsAppbar.visibility = View.GONE
                        marsViewModel.getRoverInfo()
                    }
                    ViewState.Loading -> {
                        binding.marsAppbar.visibility = View.VISIBLE
                        binding.marsAppbarContent.background =
                            AppCompatResources.getDrawable(requireContext(), R.color.loading_shadow)
                    }
                    is ViewState.LoadError -> {
                        this@MarsFragment.handleError(result.error) {
                            marsViewModel.getRoverInfo()
                        }
                    }
                    is ViewState.Successful<*> -> {
                        val roverInfo = result.data as RoverUi
                        updateAppbarInfo(roverInfo)
                        binding.marsAppbarContent.background = null
                        marsViewModel.getRoverPhotos(roverInfo.maxDate)
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            marsViewModel.roverPhotos.collect { result ->
                when (result) {
                    ViewState.Init -> {
                        initRV()
                    }
                    ViewState.Loading -> {
                        showLoadingImages()
                    }
                    is ViewState.LoadError -> {
                        this@MarsFragment.handleError(result.error) {
                            marsViewModel.getRoverPhotos()
                        }
                    }
                    is ViewState.Successful<*> -> {
                        val marsPhotoList = (result.data as List<*>).map { it as MarsPhotoUi }
                        marsPhotoAdapter?.submitList(marsPhotoList)
                        showImagesList()
                    }
                }
            }
        }
    }

    private fun showImagesList() {
        binding.run {
            loadingIndicator.visibility = View.GONE
            rvMarsPhotos.visibility = View.VISIBLE
        }
    }

    private fun showLoadingImages() {
        binding.run {
            loadingIndicator.visibility = View.VISIBLE
            rvMarsPhotos.visibility = View.GONE
        }
    }

    private fun initRV() {
        marsPhotoAdapter = MarsPhotoAdapter {
            isExpandAppbar = !binding.marsAppbar.isLifted
            showMarsBigImage(it.url)
        }
        binding.rvMarsPhotos.adapter = marsPhotoAdapter
    }

    private fun showMarsBigImage(url: String) {
        val action = MarsFragmentDirections.actionMarsFragmentToMarsImageFragment(url)
        findNavController().navigate(action)
    }

    private fun updateAppbarInfo(roverInfo: RoverUi) {
        binding.run {
            landingText.text = roverInfo.landing
            photosCountText.text = roverInfo.photosCount.toString()
            marsCollapsing.title = roverInfo.name
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}