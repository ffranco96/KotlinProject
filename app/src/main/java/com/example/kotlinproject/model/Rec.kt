package com.example.kotlinproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Used Rec as Record to avoid name conflicts with other classes
@Entity(tableName = "RECORDS")
data class Rec (
    @ColumnInfo var amount: Double = 0.0,
    @ColumnInfo var title: String = "",
    @ColumnInfo var description: String = "",
    @ColumnInfo var category: Category = Category(),
    @ColumnInfo var date:String = "",
    @ColumnInfo var currency:String = "",
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)