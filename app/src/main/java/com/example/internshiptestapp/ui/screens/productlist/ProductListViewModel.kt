package com.example.internshiptestapp.ui.screens.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshiptestapp.data.model.Product
import com.example.internshiptestapp.data.repository.ProductRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class ProductListState (
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)

class ProductListViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductListState())
    val state = _state.asStateFlow()

    private var allProducts: List<Product> = emptyList()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)

                // In real app, this would be an API call
                // val products = productRepository.getProducts()
                allProducts = ProductRepository.getProducts()

                _state.value = _state.value.copy(
                    isLoading = false,
                    products = allProducts,
                    filteredProducts = allProducts
                )

                setupSearchDebouncing()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to load products: ${e.message}"
                )
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchDebouncing() {
        // Set up search query debouncing to avoid filtering on every keystroke
        _state
            .distinctUntilChanged { old, new -> old.searchQuery == new.searchQuery }
            .debounce(300) // Wait 300ms after user stops typing
            .onEach { state ->
                if (state.searchQuery.isNotEmpty()) {
                    performFiltering(state.searchQuery)
                } else {
                    // Show all products when search is empty
                    _state.value = _state.value.copy(
                        filteredProducts = allProducts,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(
            searchQuery = query,
            isLoading = query.isNotEmpty()
        )
    }

    fun performFiltering(query: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)

                val filtered = allProducts.filter { product ->
                    product.name.contains(query, ignoreCase = true)
//                    product.description.contains(query, ignoreCase = true)
                }

                _state.value = _state.value.copy(
                    filteredProducts = filtered,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to filter products: ${e.message}"
                )
            }
        }
    }

    fun refreshProducts() {
        loadProducts()
    }
}