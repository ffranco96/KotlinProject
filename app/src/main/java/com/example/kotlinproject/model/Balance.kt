package com.example.kotlinproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BALANCES")
data class Balance (
    @ColumnInfo (name="balance_type") var type: String = "",
    @ColumnInfo (name="txs_quantity") var qtty: Int = 0,
    @ColumnInfo var amount: Double = 0.0,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)