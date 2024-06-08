package com.example.kotlinproject.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlinproject.App

@Database(entities = [Rec::class, Balance::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null
        private const val DATABASE_NAME = "myDatabase"
        fun getInstance(): AppDataBase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    App.context,
                    AppDataBase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().addTypeConverter(Converters()).build()

                INSTANCE = instance
                return instance
            }
        }
    }
    abstract fun recsDao(): RecordsDAO
    abstract fun balancesDao(): BalancesDAO

}