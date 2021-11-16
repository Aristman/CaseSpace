package ru.marslab.casespace.ui.settings.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import ru.marslab.casespace.R
import ru.marslab.casespace.ui.settings.SettingsFragment
import ru.marslab.casespace.ui.settings.SettingsViewModel
import ru.marslab.casespace.ui.theme.RoundedShapes
import ru.marslab.casespace.ui.theme.Typography

@Composable
fun ScreenSettings(
    fragment: SettingsFragment,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    settingsViewModel.getCurrentTheme()
    val currentThemeState = settingsViewModel.settingsState.collectAsState()
    val currentTheme = currentThemeState.value
    var selectedTheme by remember { mutableStateOf(currentTheme.theme) }
    val activity = fragment.requireActivity()
    Card(
        elevation = 4.dp,
        shape = RoundedShapes.medium,
        modifier = Modifier
            .padding(4.dp)
            .wrapContentHeight(align = Alignment.Top),
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.themes_choice_title),
                textAlign = TextAlign.Center,
                style = Typography.body2,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            RadioItem(
                text = stringResource(id = R.string.default_theme),
                isSelected = selectedTheme == AppTheme.DEFAULT_THEME.themeId
            ) { selectedTheme = AppTheme.DEFAULT_THEME.themeId }
            RadioItem(
                text = stringResource(id = R.string.emerald_theme),
                isSelected = selectedTheme == AppTheme.EMERALD_THEME.themeId
            ) { selectedTheme = AppTheme.EMERALD_THEME.themeId }
            RadioItem(
                text = stringResource(id = R.string.mars_theme),
                isSelected = selectedTheme == AppTheme.MARS_THEME.themeId
            ) { selectedTheme = AppTheme.MARS_THEME.themeId }

            OutlinedButton(
                onClick = {
                    settingsViewModel.saveCurrentTheme(selectedTheme)
                    activity.setTheme(selectedTheme)
                    activity.recreate()
                },
                modifier = Modifier.align(CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.apply).uppercase())
            }
        }
    }
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(onClick = {
            fragment.findNavController().popBackStack()
        }) {
            Text(text = stringResource(id = R.string.close))
        }
    }
}


internal enum class AppTheme(val themeId: Int) {
    DEFAULT_THEME(R.style.Theme_CaseSpace),
    EMERALD_THEME(R.style.Theme_CaseSpace_Emerald),
    MARS_THEME(R.style.MarsTheme)
}