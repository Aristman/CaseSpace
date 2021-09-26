package ru.marslab.casespace.ui.mars

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.domain.repository.NasaRepository
import ru.marslab.casespace.ui.mapper.toUi
import ru.marslab.casespace.ui.util.ViewState
import javax.inject.Inject

@HiltViewModel
class MarsViewModel @Inject constructor(
    private val repository: NasaRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private var roverLastPhotoDate: String? = null

    private val _roverInfo: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Init)
    val roverInfo: StateFlow<ViewState>
        get() = _roverInfo

    private val _roverPhotos: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Init)
    val roverPhotos: StateFlow<ViewState>
        get() = _roverPhotos

    fun getRoverInfo() {
        viewModelScope.launch(dispatchers.io) {
            _roverInfo.value = ViewState.Loading
            try {
                val marsRoverInfo = repository.getMarsRoverInfo()
                roverLastPhotoDate = marsRoverInfo?.maxDate
                _roverInfo.emit(marsRoverInfo?.let { ViewState.Successful(it.toUi()) }
                    ?: ViewState.LoadError(NetworkErrorException(repository.getRepoName())))
            } catch (e: Exception) {
                _roverInfo.emit(ViewState.LoadError(e))
            }
        }
    }

    fun getRoverPhotos(date: String? = roverLastPhotoDate) {
        date?.let {
            viewModelScope.launch(dispatchers.io) {
                _roverPhotos.value = ViewState.Loading
                try {
                    val marsPhotos = repository.getMarsPhotos(date)
                    _roverPhotos.emit(marsPhotos?.let { photosList ->
                        ViewState.Successful(photosList.map { it.toUi() })
                    }
                        ?: ViewState.LoadError(NetworkErrorException(repository.getRepoName())))
                } catch (e: Exception) {
                    _roverInfo.emit(ViewState.LoadError(e))
                }
            }
        }
    }
}