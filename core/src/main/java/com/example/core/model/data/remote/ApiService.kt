package com.example.core.model.data.remote

import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.entity.Credentials
import com.example.core.model.data.entity.LoginResponse
import com.example.core.model.data.entity.Order
import com.example.core.model.data.entity.OrderResponse
import com.example.core.model.data.entity.Product
import com.example.core.model.data.entity.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("foods")
    suspend fun getProducts(): List<Product>

    @GET("foods/{id}")
    suspend fun getProductById(@Path("id") id: String): Product

    @POST("users/login")
    suspend fun login(
        @Body credentials: Credentials
    ): LoginResponse

    @POST("users/register")
    suspend fun registerUser(@Body user: User): User

    @PUT("users/update/{email}")
    suspend fun updateUser(
        @Path("email") email: String,
        @Body user: User
    ): User

    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User



    @POST("orders")
    suspend fun addOrder(@Body order: Order): Order

    @GET("orders/{email}")
    suspend fun getOrderHistory(@Path("email") email: String): List<OrderResponse>

    @GET("orders/{email}/{id}")
    suspend fun getOrderDetail(
        @Path("email") email: String,
        @Path("id") id: String
    ): List<CartItem>



}