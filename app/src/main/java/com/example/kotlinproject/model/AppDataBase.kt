package com.example.kotlinproject.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Rec::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun recsDao(): RecordsDAO
}