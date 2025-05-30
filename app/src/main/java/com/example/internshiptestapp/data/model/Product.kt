package com.example.internshiptestapp.data.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class representing a product in the application.
 *
 * @property id The unique identifier for the product
 * @property name The display name of the product
 * @property description A brief description of the product
 * @property price The price of the product in USD
 * @property icon The vector icon representing the product
 */

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val icon: ImageVector
)
