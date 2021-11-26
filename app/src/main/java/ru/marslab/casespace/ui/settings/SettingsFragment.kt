package ru.marslab.casespace.ui.settings

import android.os.Bundle
import android.view.View
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import ru.marslab.casespace.R
import ru.marslab.casespace.domain.repository.Storage
import ru.marslab.casespace.ui.util.initViewNavigate

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings), DIAware {

    override val di by closestDI()
    private val storage by instance<Storage>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.compose_content).setContent {
            MaterialTheme {
                SettingsScreen(findNavController()) {
                    requireActivity().apply {
                        recreate()
                    }
                }
            }
        }
        initViewNavigate(toolbar = false)
    }

}