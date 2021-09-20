package ru.marslab.casespace.ui.earth

import android.accounts.NetworkErrorException
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.domain.repository.NasaRepository
import ru.marslab.casespace.domain.util.getNasaFormatDate
import ru.marslab.casespace.ui.util.ViewState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EarthViewModel @Inject constructor(
    private val nasaRepository: NasaRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private var _earthAssetUrl: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Init)
    val earthAssetUrl: StateFlow<ViewState>
        get() = _earthAssetUrl

    fun getEarthAsset(location: Location, date: Date) {
        _earthAssetUrl.value = ViewState.Loading
        viewModelScope.launch(dispatchers.io) {
            try {
                val earthAssets = nasaRepository.getEarthAssets(
                    lon = location.longitude.toFloat(),
                    lat = location.latitude.toFloat(),
                    date = date.getNasaFormatDate()
                )
                _earthAssetUrl.emit(
                    earthAssets?.let { ViewState.Successful(it) }
                        ?: ViewState.LoadError(NetworkErrorException(nasaRepository.getRepoName()))
                )
            } catch (e: Exception) {
                _earthAssetUrl.emit(ViewState.LoadError(e))
            }
        }
    }
}