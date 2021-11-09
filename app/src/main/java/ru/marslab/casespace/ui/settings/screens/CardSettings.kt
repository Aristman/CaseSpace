package ru.marslab.casespace.ui.settings.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.marslab.casespace.R

@Preview(showSystemUi = true)
@Composable
fun CardSettings() {
    Column(modifier = Modifier.shadow(elevation = 4.dp)) {
        Text(
            text = stringResource(id = R.string.themes_choice_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        DefaultTheme()
        EmeraldTheme()
        MarsTheme()
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.apply).uppercase())
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.close))
        }
    }
}

@Composable
fun MarsTheme() {
    SettingItem(stringResource(id = R.string.mars_theme), true) {

    }
}

@Composable
fun EmeraldTheme() {
    SettingItem(text = stringResource(id = R.string.emerald_theme), select = false) {

    }
}

@Composable
fun DefaultTheme() {
    SettingItem(text = stringResource(id = R.string.default_theme), select = false) {

    }
}

@Composable
fun SettingItem(text: String, select: Boolean, listener: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = select,
            onClick = listener
        )
        Spacer(Modifier.size(4.dp))
        Text(text = text)
    }
}

enum class AppTheme {
    DEFAULT_THEME,
    EMERALD_THEME,
    MARS_THEME
}
