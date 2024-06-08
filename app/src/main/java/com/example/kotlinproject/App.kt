package com.example.kotlinproject

import android.app.Application
import android.content.Context

class App: Application() {
    companion object{
        lateinit var context: Context
        const val RET_TRUE:Int = 1
        const val RET_FALSE:Int = 0
    }

    override fun onCreate(){
        context = applicationContext
        super.onCreate()
    }
}