package ru.marslab.casespace.ui.earth

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
import ru.marslab.casespace.ui.mapper.toUi
import ru.marslab.casespace.ui.util.ViewState
import javax.inject.Inject

@HiltViewModel
class EarthViewModel @Inject constructor(
    private val nasaRepository: NasaRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private var _earthImageList: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Init)
    val earthImageList: StateFlow<ViewState>
        get() = _earthImageList

    fun getEarthImageList(collectionType: String) {
        _earthImageList.value = ViewState.Loading
        viewModelScope.launch(dispatchers.io) {
            try {
                val epicImageList =
                    nasaRepository.getEpicImageList(collectionType, Constant.JPG_IMAGE_TYPE)
                _earthImageList.emit(
                    epicImageList?.let { imageList ->
                        ViewState.Successful(imageList.map { it.toUi() })
                    }
                        ?: ViewState.LoadError(NetworkErrorException(nasaRepository.getRepoName()))
                )
            } catch (e: Exception) {
                _earthImageList.emit(ViewState.LoadError(e))
            }
        }
    }
}