package ru.marslab.casespace.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.marslab.casespace.R
import ru.marslab.casespace.ui.custom.BaseFragment
import ru.marslab.casespace.ui.settings.screens.ScreenSettings

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private val settingsViewModel by viewModels<SettingsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(inflater.context)
            .inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.compose_content).setContent {
            MaterialTheme {
                ScreenSettings()
            }
        }
        initObservers()
        initViewNavigate(toolbar = false)
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            settingsViewModel.settingsAction.collect { action ->
                when (action) {
                    SettingsAction.ApplyTheme -> {
                        settingsViewModel.settingsState.value.theme?.let { it ->
                            requireActivity().setTheme(it)
                        }
                        requireActivity().recreate()
                    }
                    SettingsAction.Close -> {
                        findNavController().popBackStack()
                    }
                    SettingsAction.Open -> {
                    }
                }
            }
        }
    }
}