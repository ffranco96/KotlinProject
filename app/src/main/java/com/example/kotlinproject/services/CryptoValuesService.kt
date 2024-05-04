package com.example.kotlinproject.services

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class CryptoValuesService : IntentService("CryptoValuesService") {
    companion object {
        const val TAG ="CryptoValuesService"
        const val ACTION_CRYPTO_VALUES = "com.example.kotlinproject.obtained_crypto_values"
        const val EXTRA_CRYPTO_VALUES = "crypto_values"
    }

    // Executed on worker thread
    override fun onHandleIntent(intent: Intent?) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.coinpaprika.com/v1/coins/btc-bitcoin/ohlcv/latest"
        Log.d("BTC_Value","Service started")
        // Request a string response from the provided URL.
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener
            {
                //val cryptoValuesArray = JSONArray(it)
                val btcValue = it.getJSONObject(0).getDouble("high")
                Log.d("BTC_Value",btcValue.toString())
                val broadcastIntent = Intent(ACTION_CRYPTO_VALUES)
                broadcastIntent.putExtra(EXTRA_CRYPTO_VALUES, btcValue)
                sendBroadcast(broadcastIntent)
            },
            Response.ErrorListener{
                Log.d("BTC_Value", "${it.message}")
            })

        queue.add(request)
    }
}