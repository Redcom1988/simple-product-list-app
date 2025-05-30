package com.example.internshiptestapp.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.internshiptestapp.ui.screens.productdetail.ProductDetailRoute
import com.example.internshiptestapp.ui.screens.productdetail.productDetail
import com.example.internshiptestapp.ui.screens.productlist.ProductListRoute
import com.example.internshiptestapp.ui.screens.productlist.productList
import com.example.internshiptestapp.ui.theme.InternshipTestAppTheme

/**
 * Main entry point of the application.
 *
 * This activity sets up the Compose UI, theme, and navigation structure
 * for the application. It uses edge-to-edge display and defines the navigation
 * flow between product list and product detail screens.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InternshipTestAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val navController = rememberNavController()

                    // Set up navigation with NavHost
                    NavHost(
                        navController = navController,
                        startDestination = ProductListRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Product list screen with navigation to detail
                        productList(
                            onNavigateToDetail = { productId ->
                                navController.navigate(ProductDetailRoute(productId))
                            }
                        )

                        // Product detail screen with back navigation
                        productDetail(
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}