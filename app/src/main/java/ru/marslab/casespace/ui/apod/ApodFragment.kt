package ru.marslab.casespace.ui.apod

import android.accounts.NetworkErrorException
import android.os.Build
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
import ru.marslab.casespace.databinding.FragmentApodBinding
import ru.marslab.casespace.domain.model.MediaType
import ru.marslab.casespace.domain.model.PictureOfDay
import ru.marslab.casespace.domain.repository.Constant
import ru.marslab.casespace.domain.util.getNasaFormatDate
import ru.marslab.casespace.domain.util.showMessage
import ru.marslab.casespace.ui.util.ViewState
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

private const val YESTERDAY = 1L
private const val BEFORE_YESTERDAY = 2L
private const val SPLIT_CHAR = '/'

@AndroidEntryPoint
class ApodFragment : Fragment() {
    private var _binding: FragmentApodBinding? = null
    private val binding: FragmentApodBinding
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
        _binding = FragmentApodBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
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

    private fun initListeners() {
        binding.run {
            chipsDays.setOnCheckedChangeListener { _, checkedId ->
                reloadPicture(checkedId)
            }
            chipPictureHd.setOnCheckedChangeListener { _, _ ->
                reloadPicture(binding.chipsDays.checkedChipId)
            }
        }
    }


    private fun reloadPicture(checkedId: Int) {
        when (checkedId) {
            binding.chipToday.id -> {
                apodViewModel.getImageOfDay()
            }
            binding.chipYesterday.id -> {
                apodViewModel.getImageOfDay(getPostDay(YESTERDAY))
            }
            binding.chipBeforeYesterday.id -> {
                apodViewModel.getImageOfDay(getPostDay(BEFORE_YESTERDAY))
            }
        }
    }

    private fun getPostDay(minusDay: Long): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now(ZoneId.of(getString(R.string.nasa_time_zone))).minusDays(minusDay)
                .toString()
        } else {
            Date(
                Calendar.getInstance().apply {
                    timeZone = TimeZone.getTimeZone(getString(R.string.nasa_time_zone))
                    time = Date()
                    set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH) - minusDay.toInt())
                }.time.time
            ).getNasaFormatDate()
        }


    private fun initView() {
        setHasOptionsMenu(true)
        apodViewModel.getImageOfDay()
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
                binding.chipPictureHd.visibility = View.VISIBLE
                if (binding.chipPictureHd.isChecked) {
                    picture.hdUrl?.let { loadImage(it) }
                } else {
                    loadImage(picture.url)
                }
            }
            MediaType.VIDEO -> {
                binding.chipPictureHd.visibility = View.GONE
                binding.imageOfDay.load(getVideoPreviewPath(picture.url))
            }
        }
    }

    private fun getVideoPreviewPath(url: String): String {
        val videoId = url.split('/').last().split('?').first()
        return getString(R.string.path_video_preview, videoId)
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
                        reloadPicture(binding.chipsDays.checkedChipId)
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


