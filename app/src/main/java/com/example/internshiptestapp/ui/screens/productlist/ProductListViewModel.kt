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

/**
 * State holder for the product list screen.
 *
 * @property isLoading Flag indicating if data is currently being loaded
 * @property products The full list of products
 * @property filteredProducts Products filtered by the search query
 * @property searchQuery The current search query text
 * @property error Error message if any
 */
data class ProductListState (
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)

/**
 * ViewModel for the product list screen.
 *
 * Handles loading products, filtering based on search queries,
 * and updating the UI state accordingly.
 */
class ProductListViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductListState())
    val state = _state.asStateFlow()

    private var allProducts: List<Product> = emptyList()

    init {
        loadProducts()
    }

    /**
     * Loads all products from the repository.
     */
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

    /**
     * Sets up debounced search to avoid filtering on every keystroke.
     */
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

    /**
     * Updates the search query and triggers filtering.
     *
     * @param query The new search query text
     */
    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(
            searchQuery = query,
            isLoading = query.isNotEmpty()
        )
    }

    /**
     * Filters products based on the provided query string.
     *
     * @param query The search query to filter by
     */
    fun performFiltering(query: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)

                val filtered = allProducts.filter { product ->
                    product.name.contains(query, ignoreCase = true)
                    // Uncomment to also search by description
                    // || product.description.contains(query, ignoreCase = true)
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

    /**
     * Reloads products from the repository.
     */
    fun refreshProducts() {
        loadProducts()
    }
}