package com.example.kotlinproject.model

import com.example.kotlinproject.R

data class Category(var id: String = "Otro", var iconRsc: Int = R.drawable.ic_other_generic, var colorIcon: Int = R.color.sad_grey, var details: String = ""){
    fun getCategId(): String {
        return id
    }
}