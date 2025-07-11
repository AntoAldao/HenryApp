package com.example.henryapp.util

import androidx.room.TypeConverter
import com.example.henryapp.model.data.entity.CartItem
import org.json.JSONArray
import org.json.JSONObject

class CartItemListConverter {

    @TypeConverter
    fun fromCartItemList(items: List<CartItem>): String {
        val jsonArray = JSONArray()
        for (item in items) {
            val jsonObject = JSONObject()
            jsonObject.put("id", item.id)
            jsonObject.put("name", item.name)
            jsonObject.put("price", item.price)
            jsonObject.put("imageUrl", item.imageUrl)
            jsonObject.put("quantity", item.quantity)
            jsonArray.put(jsonObject)
        }
        return jsonArray.toString()
    }

    @TypeConverter
    fun toCartItemList(items: String): List<CartItem> {
        val jsonArray = JSONArray(items)
        val cartItems = mutableListOf<CartItem>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val cartItem = CartItem(
                id = jsonObject.getLong("id"),
                name = jsonObject.getString("name"),
                price = jsonObject.getDouble("price"),
                imageUrl = jsonObject.getString("imageUrl"),
                quantity = jsonObject.getInt("quantity"),
                orderId = 0 // Default value
            )
            cartItems.add(cartItem)
        }
        return cartItems
    }
}
