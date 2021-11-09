package ru.marslab.casespace.ui.settings.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.marslab.casespace.R
import ru.marslab.casespace.ui.settings.SettingsViewModel
import ru.marslab.casespace.ui.theme.RoundedShapes
import ru.marslab.casespace.ui.theme.Typography

@Composable
fun ScreenSettings(settingsViewModel: SettingsViewModel = viewModel()) {
    settingsViewModel.getCurrentTheme()
    val context = LocalContext.current
    val currentTheme by settingsViewModel.settingsState.collectAsState()
    val selectedTheme = remember {
        mutableStateOf(currentTheme.theme)
    }
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
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        selectedTheme.value = AppTheme.DEFAULT_THEME.themeId
                    }
                    .padding(8.dp)) {
                RadioButton(
                    selected = selectedTheme.value == AppTheme.DEFAULT_THEME.themeId,
                    onClick = null
                )
                Spacer(Modifier.size(4.dp))
                Text(text = stringResource(id = R.string.default_theme))
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        selectedTheme.value = AppTheme.EMERALD_THEME.themeId
                    }
                    .padding(8.dp)) {
                RadioButton(
                    selected = selectedTheme.value == AppTheme.EMERALD_THEME.themeId,
                    onClick = null
                )
                Spacer(Modifier.size(4.dp))
                Text(text = stringResource(id = R.string.emerald_theme))
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        selectedTheme.value = AppTheme.MARS_THEME.themeId
                    }
                    .padding(8.dp)) {
                RadioButton(
                    selected = selectedTheme.value == AppTheme.MARS_THEME.themeId,
                    onClick = null
                )
                Spacer(Modifier.size(4.dp))
                Text(text = stringResource(id = R.string.mars_theme))
            }


            OutlinedButton(
                onClick = {
                    selectedTheme.value?.let { settingsViewModel.saveCurrentTheme(it) }
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
            settingsViewModel.closeFragment()
        }) {
            Text(text = stringResource(id = R.string.close))
        }
    }
}

fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}


enum class AppTheme(val themeId: Int) {
    DEFAULT_THEME(R.style.Theme_CaseSpace),
    EMERALD_THEME(R.style.Theme_CaseSpace_Emerald),
    MARS_THEME(R.style.MarsTheme)
}