package com.example.kotlinproject

class loginAPI {
    companion object {
        fun login(usuario: String, contrasena: String, callback: (ok: Boolean)->Unit) {
            if (usuario=="admin" && contrasena=="123456") {
                callback(true)
            } else {
                callback(false)
            }
        }
    }
}