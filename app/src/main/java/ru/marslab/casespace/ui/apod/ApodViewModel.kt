package ru.marslab.casespace.ui.apod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.domain.repository.NasaRepository
import ru.marslab.casespace.domain.util.LoadException
import ru.marslab.casespace.domain.util.getNasaFormatDate
import ru.marslab.casespace.ui.util.ViewState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(
    private val nasaRepository: NasaRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private var _imageOfDayPath = MutableStateFlow<ViewState>(ViewState.Loading)
    val imageOfDayPath: StateFlow<ViewState>
        get() = _imageOfDayPath

    fun getImageOfDay(day: Date? = null) {
        _imageOfDayPath.value = ViewState.Loading
        try {
            viewModelScope.launch(dispatchers.io) {
                val pathToImage =
                    nasaRepository.getImageOfDay(date = (day ?: Date()).getNasaFormatDate())
                if (!pathToImage.isNullOrEmpty()) {
                    _imageOfDayPath.value = ViewState.Successful(pathToImage)
                } else {
                    _imageOfDayPath.value =
                        ViewState.LoadError(LoadException(nasaRepository.getRepoName()))
                }
            }
        } catch (e: Exception) {
            _imageOfDayPath.value = ViewState.LoadError(e)
        }
    }
}