package com.example.henryapp.model.repository

import com.example.henryapp.model.data.AppDatabase
import com.example.henryapp.model.data.dao.CartItemDao
import com.example.henryapp.model.data.dao.OrderDao
import com.example.henryapp.model.data.dao.UserDao
import com.example.henryapp.repository.CartRepository
import com.example.henryapp.repository.OrderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCartRepository(cartItemDao: CartItemDao): CartRepository {
        return CartRepository(cartItemDao)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(orderDao: OrderDao): OrderRepository {
        return OrderRepository(orderDao)
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}