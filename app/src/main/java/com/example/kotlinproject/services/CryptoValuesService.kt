package com.example.kotlinproject.services

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class CryptoValuesService : IntentService("CryptoValuesService") {
    companion object {
        const val TAG ="CryptoValuesService"
        const val ACTION_CRYPTO_VALUES = "com.example.kotlinproject.obtained_crypto_values"
        const val EXTRA_CRYPTO_VALUES = "crypto_values"
        const val ACTION_TEMP = "com.example.kotlinproject.temp_obtenida"
        const val EXTRA_TEMP = "temp"
    }

    // Executed on worker thread
    override fun onHandleIntent(intent: Intent?) {
        val queue = Volley.newRequestQueue(this)
        val ciudad = "buenosaires"
        val url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/$ciudad?unitGroup=metric&include=current&key=AFYHR8JS33UKSUHN3ZDAW5NQJ&contentType=json"

        // Request a string response from the provided URL.
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener
            {
                val temperatura = it.getJSONObject("currentConditions").getDouble("temp") //@todo delete
                Log.d("Temperatura:", "aquí")
                Log.d("Temperatura: ", temperatura.toString()) //@todo no muestra nada
                //val broadcastIntent = Intent(ACTION_TEMP)
                //broadcastIntent.putExtra(EXTRA_TEMP, temperatura)
                //sendBroadcast(broadcastIntent)
            },
            Response.ErrorListener{
            })

        queue.add(request)
    }
}