package com.example.myshoplist.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myshoplist.R
import com.example.myshoplist.Screen
import com.example.myshoplist.models.Item
import com.example.myshoplist.ui.theme.MyShopListTheme
import com.example.myshoplist.viewmodels.ItemListState
import com.example.myshoplist.viewmodels.ItemsViewModel

@Composable
fun ItemsView(
    modifier: Modifier = Modifier,
    navController: NavController,
    categoryId: String
) {
    val viewModel: ItemsViewModel = viewModel()
    val state = viewModel.state
   // val categoryName by viewModel.categoryName.collectAsState() // Collect categoryName

    ItemsViewContent(
        modifier = modifier,
        state = state.value,
        navController = navController,
        categoryId = categoryId,
        viewModel = viewModel,
        onNavigateToAddItem = {
            navController.navigate(Screen.AddItem.route + "/$categoryId")
        }
    )

    LaunchedEffect(key1 = categoryId) {
        viewModel.loadItemsForCategory(categoryId)
        viewModel.loadCategoryName(categoryId)
    }
}

@Composable
fun ItemsViewContent(
    modifier: Modifier = Modifier,
    state: ItemListState,
    navController: NavController,
    categoryId: String, // Add categoryName parameter
    onNavigateToAddItem: () -> Unit = {},
    viewModel: ItemsViewModel
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            itemsIndexed(items = state.itemList) { index, item ->
                ItemsRowView(item = item, onItemCheckedChange = { isChecked ->
                    viewModel.onItemCheckedChange(categoryId, item, isChecked)
                })
            }
        }
        Button( // Positioned independently
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp)
                .width(80.dp),
            onClick = {
                onNavigateToAddItem()
            }
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "add item",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsViewPreview() {
    val mockNavController = rememberNavController() // Create a mock NavController
    val mockViewModel: ItemsViewModel = viewModel()
    MyShopListTheme {
        ItemsViewContent(
            state = ItemListState(
                itemList = arrayListOf(
                    Item("", "Detergente Roupa", 1),
                    Item("", "Detergente Loiça", 1)
                )
            ),
            navController = mockNavController,
            categoryId = "categoryId",
            viewModel = mockViewModel
        )
    }
}