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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
            viewModel = viewModel,
            state = state,
            onNavigateToDetail = { productId ->
                onNavigateToDetail(productId)
            }
        )
    }
}

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    state: ProductListState = ProductListState(),
    onNavigateToDetail: (Int) -> Unit = {}
) {
    Column {
        SearchBar(
            query = state.searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) }
        )
        when {
            // Show loading indicator
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // Show error message
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
                         Button(onClick = viewModel::refreshProducts) {
                             Text("Retry")
                         }
                    }
                }
            }

            // Show product list
            else -> {
                LazyColumn {
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