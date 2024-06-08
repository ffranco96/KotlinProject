package com.example.kotlinproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BALANCES")
data class Balance (
    @PrimaryKey  var type: String = "",
    @ColumnInfo  var qtty: Int = 0,
    @ColumnInfo  var amount: Double = 0.0,
)