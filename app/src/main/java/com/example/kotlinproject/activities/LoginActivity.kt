package com.example.kotlinproject.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.example.kotlinproject.R
import com.example.kotlinproject.loginAPI
import com.example.kotlinproject.services.CryptoValuesService

class LoginActivity : AppCompatActivity() {
    companion object {
        const val KEY_IS_LOGGED = "isLogged"
        const val PREFS_LOGIN = "login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val editTextUser = findViewById<EditText>(R.id.editTextUser)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)

        val preferences = getSharedPreferences(PREFS_LOGIN, Context.MODE_PRIVATE)
        var isLogged = false

        if (preferences.contains(KEY_IS_LOGGED)) {
            if(preferences.getBoolean(KEY_IS_LOGGED, false)){
                goToHome()
            }
        }

        loginButton.setOnClickListener {
            val user = editTextUser.text.toString()
            val password = editTextPassword.text.toString()
            loginAPI.login(user, password){response->
                if(response){
                    goToHome()

                    // Save shared preferences
                    preferences.edit{
                        putBoolean(KEY_IS_LOGGED, true)
                    }
                } else {
                    val alertDialog = AlertDialog.Builder(this)
                        .setTitle(getString(R.string.alert_login_title))
                        .setMessage(getString(R.string.alert_login_message))
                        .setPositiveButton(getString(R.string.alert_login_accept)){ _, _ ->
                        }
                    alertDialog.show()

                }
            }

        }


    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}