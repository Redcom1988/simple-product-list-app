package com.example.internshiptestapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Smartphone
import com.example.internshiptestapp.data.model.Product

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = product.icon,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }


        }
    }
}


@Preview(showBackground = false)
@Composable
private fun PreviewProductItem() {
    ProductItem(
        product =
        Product(
            id = 1,
            name = "Phone",
            description = "A simple phone",
            price = 199.99,
            icon = Lucide.Smartphone
        )
    ) {  }
}