package com.example.myshoplist.ui.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshoplist.models.Item
import com.example.myshoplist.ui.theme.MyShopListTheme

@Composable
fun ItemsRowView(item: Item, onItemCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = { isChecked ->
                onItemCheckedChange(isChecked)
            }
        )
        Text(
            text = item.name ?: "",
            style = if (item.isChecked) {
                MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray
                )
            } else {
                MaterialTheme.typography.bodyMedium
            },
            modifier = Modifier.weight(1f)
        )
        Text(text = (item.quantity ?: 0).toString())
    }
}


@Preview(showBackground = true)
@Composable
fun ItemsRowViewPreview() {
    MyShopListTheme {
        ItemsRowView(
            item = Item( // Pass an Item object
                docId = "",
                name = "Detergente roupa",
                quantity = 1
            ),
            onItemCheckedChange = {}
        )
    }
}