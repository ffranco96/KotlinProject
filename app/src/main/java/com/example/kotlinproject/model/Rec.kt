package com.example.kotlinproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

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
): Comparable<Rec>{
    override fun compareTo(other: Rec): Int {
        val formatter = SimpleDateFormat("yyyy-MM-dd") // Adjust format if needed
        val date1 = formatter.parse(date)
        val date2 = formatter.parse(other.date)
        return date1.compareTo(date2)
    }
}