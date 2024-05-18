package com.example.kotlinproject.model

// Used Rec as Record to avoid name conflicts with other classes
data class Rec(var amount: Double = 0.0, var title: String = "", var description: String = "", var category: Category = Category(), var date:String = "", var currency:String = "") //@todo initialize Category, how? how to access to categories list in the provider? date modify to Date