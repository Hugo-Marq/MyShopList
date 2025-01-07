package com.example.myshoplist.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.myshoplist.models.Category
import com.example.myshoplist.ui.theme.MyShopListTheme
import com.example.myshoplist.viewmodels.CategoriesViewModel
import com.example.myshoplist.viewmodels.CategoryListState

@Composable
fun CategoriesView(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: CategoriesViewModel = viewModel()
    val state by viewModel.state
    CategoriesViewContent(
        modifier = modifier,
        state = state,
        navController = navController,
        onAddCategory = viewModel::addCategory,
        onToggleAddCategoryPopup = viewModel::toggleAddCategoryPopup,
        onDeleteCategory = viewModel::deleteCategory,
        onStartEditingCategory = viewModel::startEditingCategory,
        onUpdateCategory = viewModel::updateCategory,
        onCancelEditingCategory = viewModel::cancelEditingCategory,
        onStartSharingCategory = viewModel::startSharingCategory,
        onShareCategory = viewModel::shareCategory,
        onCancelSharingCategory = viewModel::cancelSharingCategory
    )

    LaunchedEffect(Unit) {
        viewModel.loadCategoriesList()
    }
}
@Composable
fun CategoriesViewContent(
    modifier: Modifier = Modifier,
    state: CategoryListState,
    navController: NavController,
    onAddCategory: (String, String) -> Unit,
    onToggleAddCategoryPopup: () -> Unit,
    onDeleteCategory: (String) -> Unit,
    onStartEditingCategory: (Category) -> Unit,
    onUpdateCategory: (Category) -> Unit,
    onCancelEditingCategory: () -> Unit,
    onStartSharingCategory: (Category) -> Unit,
    onShareCategory: (String, String) -> Unit,
    onCancelSharingCategory: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                itemsIndexed(items = state.categoryList) { _, item ->
                    CategoriesRowView(
                        category = item,
                        navController = navController,
                        onDeleteCategory = onDeleteCategory,
                        onEditCategory = onStartEditingCategory, // Updated
                        onShareCategory = onStartSharingCategory // Updated
                    )
                }
            }
        }
        if (state.isEditingCategory) {
            EditCategoryPopup(
                category = state.editingCategory,
                onEditCategory = onUpdateCategory,
                onDismissRequest = onCancelEditingCategory
            )
        }
        if (state.isSharingCategory) {
            ShareCategoryPopup(
                category = state.sharingCategory!!,
                onShare = { email ->
                    state.sharingCategory?.docId?.let { categoryId ->
                        onShareCategory(categoryId, email)
                    }
                },
                onDismissRequest = onCancelSharingCategory
            )
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp)
                .width(80.dp),
            onClick = { onToggleAddCategoryPopup() }
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "Add Category",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        if (state.isAddingCategory) {
            AddCategoryPopup(
                onAddCategory = onAddCategory,
                onDismissRequest = onToggleAddCategoryPopup
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoriesViewPreview() {
    MyShopListTheme {
        CategoriesViewContent(
            state = CategoryListState(
                categoryList = listOf(
                    Category("", "Home Shopping", "Groceries for home"),
                    Category("", "Office Shopping", "Supplies for office")
                ),
                isAddingCategory = true
            ),
            navController = rememberNavController(), // Mock NavController
            onAddCategory = { _, _ -> },
            onToggleAddCategoryPopup = {},
            onDeleteCategory = {},
            onStartEditingCategory = {},
            onUpdateCategory = {},
            onCancelEditingCategory = {},
            onStartSharingCategory = {},
            onShareCategory = { _, _ -> },
            onCancelSharingCategory = {}
        )
    }
}
