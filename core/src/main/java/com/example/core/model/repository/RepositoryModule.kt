package com.example.core.model.repository

import com.example.core.model.data.AppDatabase
import com.example.core.model.data.dao.CartItemDao
import com.example.core.model.data.dao.UserDao
import com.example.core.model.data.remote.ApiService
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
    fun provideCartRepository(cartItemDao: CartItemDao, apiService: ApiService): CartRepository {
        return CartRepository(cartItemDao, apiService)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(apiService: ApiService): OrderRepository {
        return OrderRepository(apiService)
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}