package ru.marslab.casespace.ui.apod

import android.accounts.NetworkErrorException
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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentApodContentBinding
import ru.marslab.casespace.domain.model.MediaType
import ru.marslab.casespace.domain.model.PictureOfDay
import ru.marslab.casespace.domain.model.PostDay
import ru.marslab.casespace.domain.repository.Constant
import ru.marslab.casespace.domain.util.showMessage
import ru.marslab.casespace.ui.util.ViewState
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class ApodContentFragment : Fragment() {
    companion object {
        const val POST_DAY_TAG = "post_day"
    }

    private var postDay: PostDay? = null
    private var _binding: FragmentApodContentBinding? = null
    private val binding: FragmentApodContentBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    private val bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout> by lazy {
        BottomSheetBehavior.from(
            binding.apodBottomSheet.root
        )
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_search -> {
                requireView().showMessage(R.string.search)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        postDay = arguments?.getParcelable<PostDay>(POST_DAY_TAG)
        apodViewModel.getImageOfDay(postDay)
        binding.videoPlayer.settings.apply {
            javaScriptEnabled = true
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                apodViewModel.imageOfDayPath.collect { result ->
                    when (result) {
                        is ViewState.LoadError -> {
                            handleError(result.error)
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

    private fun handleError(error: Throwable) {
        when (error) {
            is NetworkErrorException -> {
                Snackbar.make(
                    requireView(),
                    error.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            is UnknownHostException,
            is SocketTimeoutException -> {
                Snackbar.make(
                    requireView(),
                    Constant.NO_INTERNET_CONNECTION,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(R.string.repeat) {
                        apodViewModel.getImageOfDay(postDay)
                    }
                    .show()
            }
            else -> {
                throw error
            }
        }
    }

    private fun updateBottomSheet(picture: PictureOfDay) {
        binding.apodBottomSheet.run {
            bottomSheetTitle.text = picture.title
            bottomSheetContent.text = picture.description
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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


