package com.example.internshiptestapp.ui.screens.productdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailRoute(
    val productId: Int = 0
)

fun NavGraphBuilder.productDetail(
    onBack: () -> Unit
) {
    composable<ProductDetailRoute> { navBackStackEntry ->
        val route = navBackStackEntry.toRoute<ProductDetailRoute>()
        val viewModel: ProductDetailViewModel = viewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(route.productId) {
            viewModel.loadProduct(route.productId)
        }

        ProductDetailScreen(
            state = state,
            onBack = onBack,
            onRetry = { viewModel.loadProduct(route.productId) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen (
    state: ProductDetailState,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar with back button
        TopAppBar(
            title = { Text("Product Details") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        when {
            // Loading state
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = state.error,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Button(onClick = onRetry) {
                            Text("Retry")
                        }
                    }
                }
            }

            state.product != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = state.product.icon,
                        contentDescription = null
                    )
                    Text(
                        text = state.product.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Price: $${state.product.price}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Description:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = state.product.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}