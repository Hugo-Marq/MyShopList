package com.example.myshoplist

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object CategoriesList : Screen("categories_list")
    object ItemsList : Screen("items_list")
    object AddCategory : Screen("add_category")
    object AddItem : Screen("add_item")
    object Register : Screen("register")
}
