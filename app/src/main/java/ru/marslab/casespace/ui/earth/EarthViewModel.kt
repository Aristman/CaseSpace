package ru.marslab.casespace.ui.earth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.marslab.casespace.domain.repository.NasaRepository
import javax.inject.Inject

@HiltViewModel
class EarthViewModel @Inject constructor(
    private val nasaRepository: NasaRepository
) : ViewModel() {
    private var _earthAssetUrl: MutableStateFlow<String> = MutableStateFlow("")
    val earthAssetUrl: StateFlow<String>
        get() = _earthAssetUrl
}