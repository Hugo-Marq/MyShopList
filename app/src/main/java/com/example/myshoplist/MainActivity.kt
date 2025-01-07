package com.example.myshoplist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myshoplist.ui.login.LoginView
import com.example.myshoplist.ui.login.RegisterView
import com.example.myshoplist.ui.theme.MyShopListTheme
import com.example.myshoplist.ui.views.AddItemView
import com.example.myshoplist.ui.views.CategoriesView
import com.example.myshoplist.ui.views.ItemsView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

const val TAG = "myshoppinglist"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            MyShopListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route
                    ) {
                        composable(Screen.Login.route) {
                            LoginView(
                                onLoginSuccess = { navController.navigate(Screen.CategoriesList.route) },
                                onRegisterClick = { navController.navigate(Screen.Register.route) }
                            )
                        }
                        composable(Screen.CategoriesList.route) {
                            CategoriesView(navController = navController)
                        }
                        composable(
                            route = Screen.ItemsList.route + "/{categoryId}",
                            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
                            ItemsView(navController = navController, categoryId = categoryId)
                        }
                        composable(
                            route = Screen.AddItem.route + "/{categoryId}",
                            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
                            AddItemView(navController = navController, categoryId = categoryId)
                        }
                        composable(Screen.Register.route) {
                            RegisterView(
                                onRegisterSuccess = { navController.navigate(Screen.CategoriesList.route) },
                                onBackToLogin = { navController.navigate(Screen.Login.route) }
                            )
                        }
                    }
                }
            }

            LaunchedEffect(Unit) {
                val auth = Firebase.auth
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    navController.navigate(Screen.CategoriesList.route)
                }
            }
        }
    }
}


