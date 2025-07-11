package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.model.data.AppDatabase
import com.example.core.model.data.dao.CartItemDao
import com.example.core.model.data.dao.OrderDao
import com.example.core.model.data.migrations.MIGRATION_1_2
import com.example.core.model.data.migrations.MIGRATION_2_3
import com.example.core.model.data.migrations.MIGRATION_3_4
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "henry_app_database"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
    }

    @Provides
    fun provideCartItemDao(database: AppDatabase): CartItemDao {
        return database.cartItemDao()
    }
    @Provides
    fun provideOrderDao(database: AppDatabase): OrderDao {
        return database.orderDao()
    }
}
