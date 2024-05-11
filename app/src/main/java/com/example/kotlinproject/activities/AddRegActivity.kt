package com.example.kotlinproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinproject.R
import com.example.kotlinproject.model.Rec

class AddRegActivity : AppCompatActivity() {
    val newRecord = Rec()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reg)
    }

    override fun onRestart() {
        Log.d("LifeCycle", "onRestart")
        super.onRestart()
    }

    override fun onPause() {
        Log.d("LifeCycle", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("LifeCycle", "onStop")
        super.onStop()
    }
}