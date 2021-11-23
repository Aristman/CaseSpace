package ru.marslab.casespace.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun RadioGroup(
    items: List<String>,
    selectItem: Int = 0,
    onSelected: (Pair<Int, String>) -> Unit
) {
    var selectItemIndex by rememberSaveable {
        mutableStateOf(selectItem)
    }
    Column {
        items.forEachIndexed { index, item ->
            RadioItem(text = item, isSelect = selectItemIndex == index) { selectItemText ->
                selectItemIndex = index
                onSelected(Pair(index, selectItemText))
            }
        }
    }
}

private val itemList = (1..9).map { "item $it" }

@Preview(showBackground = true)
@Composable
fun RadioItemPreView() {
    Column {
        var radioSelectedItem by rememberSaveable { mutableStateOf(itemList.first()) }
        RadioGroup(items = itemList) {
            radioSelectedItem = it.second
        }
        Text(text = radioSelectedItem)
    }
}
