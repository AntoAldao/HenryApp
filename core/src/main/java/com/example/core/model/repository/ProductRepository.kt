package com.example.core.model.repository

import com.example.core.model.data.entity.Product
import com.example.core.model.data.remote.ApiService
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getProducts(): List<Product> {
        return apiService.getProducts()
    }

    suspend fun getProductById(productId: String): Product {
        return apiService.getProductById(productId)
    }
}