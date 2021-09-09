package ru.marslab.casespace.ui.apod

import android.accounts.NetworkErrorException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import ru.marslab.casespace.domain.model.Picture
import ru.marslab.casespace.ui.util.ViewState
import java.time.DayOfWeek
import java.util.*

private const val YESTERDAY = -1
private const val BEFORE_YESTERDAY = -2

@AndroidEntryPoint
class ApodFragment : Fragment() {
    private var _binding: FragmentApodBinding? = null
    private val binding: FragmentApodBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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

    private fun FragmentApodBinding.reloadPicture(checkedId: Int) {
        when (checkedId) {
            chipToday.id -> {
                apodViewModel.getImageOfDay()
            }
            chipYesterday.id -> {
                apodViewModel.getImageOfDay(getPostDay(YESTERDAY))
            }
            chipBeforeYesterday.id -> {
                apodViewModel.getImageOfDay(getPostDay(BEFORE_YESTERDAY))
            }
        }
    }

    private fun getPostDay(minusDay: Int): Date? =
        Calendar.getInstance().apply {
            time = Date()
            set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH) + minusDay)
        }.time


    private fun initView() {
        apodViewModel.getImageOfDay()
        bottomSheetBehavior = BottomSheetBehavior.from(binding.apodBottomSheet.root)
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
                            val picture = result.data as Picture
                            showMainContent()
                            if (binding.chipPictureHd.isChecked) {
                                loadImage(picture.hdUrl)
                            } else {
                                loadImage(picture.url)
                            }
                            updateBottomSheet(picture)
                        }
                    }
                }
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
            else -> {
                throw error
            }
        }
    }

    private fun updateBottomSheet(picture: Picture) {
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