package com.example.internshiptestapp.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val icon: ImageVector
)
