package com.example.internshiptestapp.data.repository

import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Keyboard
import com.composables.icons.lucide.Laptop
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Monitor
import com.composables.icons.lucide.Mouse
import com.composables.icons.lucide.PcCase
import com.composables.icons.lucide.RadioReceiver
import com.composables.icons.lucide.Server
import com.composables.icons.lucide.Smartphone
import com.composables.icons.lucide.Tablet
import com.example.internshiptestapp.data.model.Product

object ProductRepository {
    private val productList = listOf(
        Product(
            id = 1,
            name = "Phone",
            description = "A simple phone",
            price = 199.99,
            icon = Lucide.Smartphone
        ),
        Product(
            id = 2,
            name = "Laptop",
            description = "A simple laptop",
            price = 599.99,
            icon = Lucide.Laptop
        ),
        Product(
            id = 3,
            name = "Desktop PC",
            description = "A simple computer",
            price = 399.99,
            icon = Lucide.PcCase
        ),
        Product(
            id = 4,
            name = "Mini PC",
            description = "A smaller computer",
            price = 499.99,
            icon = Lucide.Server
        ),
        Product(
            id = 5,
            name = "Console",
            description = "A simple console",
            price = 499.99,
            icon = Lucide.RadioReceiver
        ),
        Product(
            id = 5,
            name = "Camera",
            description = "A simple camera",
            price = 299.99,
            icon = Lucide.Camera
        ),
        Product(
            id = 6,
            name = "Tablet",
            description = "A simple tablet",
            price = 250.0,
            icon = Lucide.Tablet
        ),
        Product(
            id = 7,
            name = "Monitor",
            description = "A simple monitor",
            price = 99.99,
            icon = Lucide.Monitor
        ),
        Product(
            id = 8,
            name = "Mouse",
            description = "A simple peripheral",
            price = 19.99,
            icon = Lucide.Mouse
        ),
        Product(
            id = 9,
            name = "Keyboard",
            description = "A simple peripheral",
            price = 29.99,
            icon = Lucide.Keyboard
        )
    )

    fun getProducts(): List<Product> {
        return productList
    }

    fun getProductById(id: Int): Product? {
        return productList.find { it.id == id }
    }
}