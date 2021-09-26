package ru.marslab.casespace.ui.apod

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentApodContentBinding
import ru.marslab.casespace.domain.model.MediaType
import ru.marslab.casespace.domain.model.PictureOfDay
import ru.marslab.casespace.domain.model.PostDay
import ru.marslab.casespace.domain.util.handleError
import ru.marslab.casespace.ui.util.ViewState

@AndroidEntryPoint
class ApodContentFragment : Fragment() {
    companion object {
        const val POST_DAY_TAG = "post_day"
    }

    private var postDay: PostDay? = null
    private var _binding: FragmentApodContentBinding? = null
    private val binding: FragmentApodContentBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null

    private val apodViewModel by viewModels<ApodViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApodContentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.apodBottomSheet.root)
        setHasOptionsMenu(true)
        postDay = arguments?.getParcelable(POST_DAY_TAG)
        apodViewModel.getImageOfDay(postDay)
        binding.videoPlayer.settings.apply {
            javaScriptEnabled = true
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                apodViewModel.imageOfDayPath.collect { result ->
                    when (result) {
                        is ViewState.LoadError -> {
                            this@ApodContentFragment.handleError(result.error) {
                                apodViewModel.getImageOfDay(
                                    postDay
                                )
                            }
                        }
                        ViewState.Loading -> {
                            showLoading()
                        }
                        is ViewState.Successful<*> -> {
                            val picture = result.data as PictureOfDay
                            showMainContent()
                            updateBottomSheet(picture)
                            updateUi(picture)
                        }
                        ViewState.Init -> {
                        }
                    }
                }
            }
        }
    }

    private fun updateUi(picture: PictureOfDay) {
        when (picture.type) {
            MediaType.PHOTO -> {
                binding.imageOfDay.visibility = View.VISIBLE
                binding.videoPlayer.visibility = View.GONE
                loadImage(picture.url)
            }
            MediaType.VIDEO -> {
                binding.imageOfDay.visibility = View.GONE
                binding.videoPlayer.visibility = View.VISIBLE
                binding.videoPlayer.loadUrl(picture.url)
            }
        }
    }

    private fun updateBottomSheet(picture: PictureOfDay) {
        binding.apodBottomSheet.run {
            bottomSheetTitle.text = picture.title
            bottomSheetContent.text = picture.description
        }
        bottomSheetBehavior?.let { it.state = BottomSheetBehavior.STATE_COLLAPSED }
    }

    private fun loadImage(imageUrl: String) {
        binding.imageOfDay.load(imageUrl)
    }

    private fun showLoading() {
        binding.run {
            apodMainContent.visibility = View.GONE
            loadingIndicator.visibility = View.VISIBLE
        }
    }

    private fun showMainContent() {
        binding.run {
            apodMainContent.visibility = View.VISIBLE
            loadingIndicator.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}


