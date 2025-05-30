package com.example.internshiptestapp.ui.screens.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshiptestapp.data.model.Product
import com.example.internshiptestapp.data.repository.ProductRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * State holder for the product detail screen.
 *
 * @property product The product to display, or null if not loaded yet
 * @property isLoading Flag indicating if data is currently being loaded
 * @property error Error message if any
 */
data class ProductDetailState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the product detail screen.
 *
 * Handles loading the specific product details and updating the UI state.
 *
 * @property repository The repository to load product data from
 */
class ProductDetailViewModel(
    private val repository: ProductRepository = ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state.asStateFlow()

    /**
     * Loads a specific product by its ID.
     *
     * @param productId The ID of the product to load
     */
    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val product = repository.getProductById(productId)
                if (product != null) {
                    _state.value = _state.value.copy(
                        product = product,
                        isLoading = false,
                        error = null
                    )
                } else {
                    _state.value = _state.value.copy(
                        product = null,
                        isLoading = false,
                        error = "Product not found"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    product = null,
                    isLoading = false,
                    error = e.message ?: "Failed to load product"
                )
            }
        }
    }
}