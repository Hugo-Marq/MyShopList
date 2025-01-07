package com.example.myshoplist.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myshoplist.Screen
import com.example.myshoplist.models.Category
import com.example.myshoplist.ui.theme.MyShopListTheme

@Composable
fun CategoriesRowView(
    modifier: Modifier = Modifier,
    category: Category,
    navController: NavController,
    onDeleteCategory: (String) -> Unit,
    onEditCategory: (Category) -> Unit,
    onShareCategory: (Category) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        navController.navigate(Screen.ItemsList.route + "/${category.docId}")
                    },
                text = category.name.orEmpty()
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = category.description.orEmpty()
            )
        }

        IconButton(
            modifier = Modifier.padding(end = 8.dp),
            onClick = { onEditCategory(category) } // Pass the category object
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Category")
        }

        IconButton(
            modifier = Modifier.padding(end = 16.dp),
            onClick = { category.docId?.let { onDeleteCategory(it) } }
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Category")
        }
        IconButton(
            modifier = Modifier.padding(end = 8.dp),
            onClick = { onShareCategory(category) } // Pass the category object
        ) {
            Icon(Icons.Default.Share, contentDescription = "Share Category")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryRowViewPreview() {
    MyShopListTheme {
        CategoriesRowView(
            category = Category(
                docId = "1", // Ensure this is a non-empty valid ID
                name = "Compras de casa",
                description = "As compras que são para casa"
            ),
            navController = rememberNavController(), // Pass a dummy NavController for preview
            onDeleteCategory = {},
            onEditCategory = {},
            onShareCategory = {}
        )
    }
}
