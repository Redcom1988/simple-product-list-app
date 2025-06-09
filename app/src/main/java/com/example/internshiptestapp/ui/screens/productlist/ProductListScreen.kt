package com.example.internshiptestapp.ui.screens.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.internshiptestapp.data.repository.ProductRepository
import com.example.internshiptestapp.ui.components.ProductItem
import com.example.internshiptestapp.ui.components.SearchBar
import kotlinx.serialization.Serializable

@Serializable
object ProductListRoute

fun NavGraphBuilder.productList(
    onNavigateToDetail: (Int) -> Unit
) {
    composable<ProductListRoute> {
        val viewModel: ProductListViewModel = viewModel()
        val state by viewModel.state.collectAsState()

        ProductListScreen(
            state = state,
            onRefresh = { viewModel.refreshProducts() },
            onSearchQueryChange = { query -> viewModel.onSearchQueryChange(query) },
            onNavigateToDetail = { productId ->
                onNavigateToDetail(productId)
            }
        )
    }
}


/**
 * Main composable for the product list screen.
 *
 * Displays a list of products with search functionality, loading indicator,
 * and error handling.
 *
 * @param onNavigateToDetail Callback for when a product is selected
 */
@Composable
fun ProductListScreen(
    state: ProductListState = ProductListState(),
    onRefresh: () -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {},
    onNavigateToDetail: (Int) -> Unit = {}
) {
    Column {
        SearchBar(
            query = state.searchQuery,
            onQueryChange = { newQuery -> onSearchQueryChange(newQuery) }
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

            // Error state
            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.error,
                            modifier = Modifier.padding(16.dp)
                        )
                         Button(onClick = onRefresh) {
                             Text("Retry")
                         }
                    }
                }
            }

            // Success State
            else -> {
                LazyColumn (
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(state.filteredProducts) { product ->
                        ProductItem(
                            product = product,
                            onClick = {
                                onNavigateToDetail(product.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewProductListScreen() {
    ProductListScreen(
        state = ProductListState(
            products = ProductRepository.getProducts(),
            filteredProducts = ProductRepository.getProducts()
        )
    ) {}
}