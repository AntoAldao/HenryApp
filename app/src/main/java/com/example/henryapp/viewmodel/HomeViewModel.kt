package com.example.henryapp.viewmodel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.henryapp.model.data.entity.Product

class HomeViewModel : ViewModel() {
    private val allProducts = listOf(
        Product("1", "Pizza Margherita", "https://www.novachef.es/media/images/pizza-pepperoni.jpg", "Pizza", 12.45),
        Product("2", "Cheeseburger", "https://s7d1.scene7.com/is/image/mcdonaldsstage/DC_202302_0003-999_CheeseburgerAlt_1564x1564:product-header-mobile?wid=1313&hei=1313&dpr=off", "Burger", 8.50),
        Product("3", "Pasta Alfredo", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcROLaERMLL2bW-xycPIDlcFAH0SzxqQQW3Ofg&s", "Pasta", 10.00)
    )

    val products = mutableStateListOf<Product>().apply { addAll(allProducts) }
    val searchQuery = mutableStateOf("")

    fun filterProducts(query: String) {
        searchQuery.value = query
        products.clear()
        products.addAll(
            allProducts.filter {
                it.name.contains(query, ignoreCase = true)
            }
        )
    }
}
