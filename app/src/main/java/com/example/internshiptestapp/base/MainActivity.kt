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

                    NavHost(
                        navController = navController,
                        startDestination = ProductListRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        productList(
                            onNavigateToDetail = { productId ->
                                navController.navigate(ProductDetailRoute(productId))
                            }
                        )

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