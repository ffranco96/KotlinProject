package com.example.kotlinproject.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun fromCategory(category: Category): String {
        return "${category.id},${category.iconRsc},${category.colorIcon},${category.details}"
    }

    @TypeConverter
    fun toCategory(value: String): Category {
        val parts = value.split(",")
        return Category(parts[0], parts[1].toInt(), parts[2].toInt(), parts[3])
    }
}