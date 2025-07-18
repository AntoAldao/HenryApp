package com.example.henryapp.viewmodel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.core.model.data.entity.Product
import com.example.core.model.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    val products = mutableStateListOf<Product>()
    val searchQuery = mutableStateOf("")

    private var allProducts = listOf<Product>()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        // Ejecutar en una coroutine del ViewModel
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            try {
                val productList = productRepository.getProducts() // suspend fun
                allProducts = productList
                products.clear()
                products.addAll(productList)
            } catch (e: Exception) {
                // Podés manejar errores de red acá si querés mostrar algo
                e.printStackTrace()
            }
        }
    }

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

