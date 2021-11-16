package ru.marslab.casespace.ui.settings.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioItem(text: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick.invoke() }
            .padding(8.dp)) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Spacer(Modifier.size(4.dp))
        Text(text = text)
    }
}