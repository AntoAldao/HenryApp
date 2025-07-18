package com.example.core.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.model.data.dao.CartItemDao
import com.example.core.model.data.dao.OrderDao
import com.example.core.model.data.dao.UserDao
import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.entity.Order
import com.example.core.model.data.entity.User
import com.example.core.model.data.migrations.MIGRATION_1_2
import com.example.core.model.data.migrations.MIGRATION_2_3
import com.example.core.model.data.migrations.MIGRATION_3_4
import com.example.core.utils.Converters

@Database(entities = [CartItem::class, Order::class, User::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
    abstract fun orderDao(): OrderDao
    abstract fun userDao(): UserDao
//
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "henry_app_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}