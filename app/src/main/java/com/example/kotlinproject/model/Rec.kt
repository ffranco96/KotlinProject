package com.example.kotlinproject.model

// Used Rec as Record to avoid name conflicts with other classes
data class Rec(var amount: Double = 0.0, var title: String = "", var description: String = "", var category: String = "", var date:String = "", var currency:String = "")