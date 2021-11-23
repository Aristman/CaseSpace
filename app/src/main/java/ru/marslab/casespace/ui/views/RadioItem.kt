package ru.marslab.casespace.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun RadioItem(
    text: String,
    modifier: Modifier = Modifier,
    isSelect: Boolean = false,
    onClickItem: (String) -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClickItem(text) }
            .fillMaxWidth(),
        verticalAlignment = CenterVertically
    ) {
        RadioButton(selected = isSelect, onClick = { onClickItem(text) })
        val annotatedString = buildAnnotatedString { append(text) }
        Text(text = annotatedString)
    }
}
