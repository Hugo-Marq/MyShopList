package com.example.myshoplist.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myshoplist.viewmodels.AddItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemView(navController: NavController, categoryId: String) {
    val viewModel = remember { AddItemViewModel() }
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Item") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Item Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            OutlinedTextField(
                value = state.quantity.toString(),
                onValueChange = { viewModel.onQuantityChange(it) },
                label = { Text("Quantity") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Button(
                onClick = {
                    viewModel.addItem(categoryId)
                    navController.popBackStack() // Navigate back after adding
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Add Item")
            }
        }
    }
}
