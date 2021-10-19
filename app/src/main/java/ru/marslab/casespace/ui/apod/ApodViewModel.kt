package ru.marslab.casespace.ui.apod

import android.accounts.NetworkErrorException
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.domain.interactor.ApodInteractor
import ru.marslab.casespace.domain.model.PostDay
import ru.marslab.casespace.domain.repository.Constant
import ru.marslab.casespace.domain.util.getNasaFormatDate
import ru.marslab.casespace.ui.util.ViewState
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(
    private val apodInteractor: ApodInteractor,
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private var _imageOfDayPath = MutableStateFlow<ViewState>(ViewState.Loading)
    val imageOfDayPath: StateFlow<ViewState>
        get() = _imageOfDayPath

    fun getImageOfDay(day: PostDay?) {
        viewModelScope.launch(dispatchers.io) {
            _imageOfDayPath.emit(ViewState.Loading)
            try {
                val picture =
                    apodInteractor.getPictureOfDay(date = day?.let { getPostDay(it.minusDay) })
                _imageOfDayPath.emit(
                    picture?.let { ViewState.Successful(it) } ?: ViewState.LoadError(
                        NetworkErrorException(Constant.getLoadErrorString(apodInteractor.getRepoName()))
                    )
                )
            } catch (e: Exception) {
                _imageOfDayPath.emit(ViewState.LoadError(e))
            }
        }
    }

    private fun getPostDay(minusDay: Long): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now(ZoneId.of(Constant.NASA_TIME_ZONE)).minusDays(minusDay)
                .toString()
        } else {
            Date(
                Calendar.getInstance().apply {
                    timeZone = TimeZone.getTimeZone(Constant.NASA_TIME_ZONE)
                    time = Date()
                    set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH) - minusDay.toInt())
                }.time.time
            ).getNasaFormatDate()
        }
}