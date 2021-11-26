package ru.marslab.casespace.ui.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.kodein.di.compose.rememberInstance
import ru.marslab.casespace.R
import ru.marslab.casespace.domain.repository.Storage
import ru.marslab.casespace.ui.theme.RoundedShapes
import ru.marslab.casespace.ui.theme.Typography
import ru.marslab.casespace.ui.views.RadioGroup

@Composable
fun SettingsScreen(
    navController: NavController,
    onThemeSelect: (themeId: Int) -> Unit
) {
    val storage by rememberInstance<Storage>()
    val currentTheme = remember {
        storage.getTheme()
    }
    Card(
        elevation = 4.dp,
        shape = RoundedShapes.medium,
        modifier = Modifier
            .padding(4.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
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
            RadioGroup(
                items = AppTheme.values().map { stringResource(id = it.themeName) },
                selectItem = AppTheme.values().find { it.themeId == currentTheme }?.ordinal
                    ?: 0
            ) {
                val themeId = AppTheme.values()[it.first].themeId
                storage.saveTheme(themeId)
                onThemeSelect(themeId)
            }
        }
    }
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = stringResource(id = R.string.close))
        }
    }
}


internal enum class AppTheme(val themeId: Int, @StringRes val themeName: Int) {
    DEFAULT_THEME(R.style.Theme_CaseSpace, R.string.default_theme),
    EMERALD_THEME(R.style.Theme_CaseSpace_Emerald, R.string.emerald_theme),
    MARS_THEME(R.style.MarsTheme, R.string.mars_theme)
}