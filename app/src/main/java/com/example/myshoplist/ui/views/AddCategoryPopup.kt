package com.example.myshoplist.ui.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myshoplist.ui.theme.MyShopListTheme
import com.example.myshoplist.viewmodels.AddCategoryViewModel
import com.example.myshoplist.viewmodels.ItemsViewModel


@Composable
fun AddCategoryPopup(
    onAddCategory: (String, String) -> Unit,
    onDismissRequest: () -> Unit
) {

    val viewModel: AddCategoryViewModel = viewModel()
    val state = viewModel.state

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = state.value.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Category Name") }
        )
        TextField(
            value = state.value.description,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("Category Description") }
        )
        Button(
            onClick = {
                // Log the category info here
                Log.d("AddCategoryPopup", "Adding category: ${state.value.name}, ${state.value.description}")
                if (state.value.name.isNotBlank() && state.value.description.isNotBlank()) {
                    onAddCategory(state.value.name, state.value.description)
                }
                onDismissRequest()
            }
        ) {
            Text("Add Category")
        }
        Button(
            onClick = onDismissRequest
        ) {
            Text("Cancel")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AddCategoryPopupPreview() {
    MyShopListTheme {
        AddCategoryPopup(
            onAddCategory = { _, _ -> },
            onDismissRequest = { }
        )
    }
}
