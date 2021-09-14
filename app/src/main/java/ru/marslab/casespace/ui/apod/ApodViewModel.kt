package ru.marslab.casespace.ui.apod

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.domain.repository.Constant
import ru.marslab.casespace.domain.repository.NasaRepository
import ru.marslab.casespace.ui.util.ViewState
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(
    private val nasaRepository: NasaRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private var _imageOfDayPath = MutableStateFlow<ViewState>(ViewState.Loading)
    val imageOfDayPath: StateFlow<ViewState>
        get() = _imageOfDayPath

    fun getImageOfDay(day: String? = null) {
        viewModelScope.launch(dispatchers.io) {
            _imageOfDayPath.emit(ViewState.Loading)
            try {
                val picture =
                    nasaRepository.getPictureOfDay(date = day)
                _imageOfDayPath.emit(
                    picture?.let { ViewState.Successful(it) } ?: ViewState.LoadError(
                        NetworkErrorException(Constant.getLoadErrorString(nasaRepository.getRepoName()))
                    )
                )
            } catch (e: Exception) {
                _imageOfDayPath.emit(ViewState.LoadError(e))
            }
        }
    }
}