package com.example.henryapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.data.entity.Product
import com.example.core.model.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    val products = mutableStateListOf<Product>()
    val searchQuery = mutableStateOf("")

    private var allProducts = listOf<Product>()

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents = _errorEvents.asSharedFlow()

    val minPriceFilter = mutableStateOf<Double?>(null)
    val maxPriceFilter = mutableStateOf<Double?>(null)

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val productList = productRepository.getProducts()
                allProducts = productList
                products.clear()
                products.addAll(productList)
            } catch (e: Exception) {
                _errorEvents.emit("Error al cargar productos: ${e.message}")
            }
            finally {
                _isLoading.value = false
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

    fun filterProductsByPrice(minPrice: Double, maxPrice: Double) {
        minPriceFilter.value = minPrice
        maxPriceFilter.value = maxPrice
        products.clear()
        products.addAll(
            allProducts.filter {
                it.price in minPrice..maxPrice
            }
        )
    }
    fun clearPriceFilter() {
        minPriceFilter.value = null
        maxPriceFilter.value = null
        products.clear()
        products.addAll(allProducts)
    }

    suspend fun getProductById(id: String): Product {
        _isLoading.value = true
        return try {
            productRepository.getProductById(id)
        } catch (e: Exception) {
            viewModelScope.launch {
                _errorEvents.emit("Error al obtener el producto")
            }
            return Product(
                _id = "",
                name = "",
                price = 0.0,
                imageUrl = "",
                hasDrink = false,
                description = ""
            )
        }
        finally {
            _isLoading.value = false
        }
    }
}
