package com.example.kotlinproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CryptoValuesReceiver : BroadcastReceiver() {
    // As this is enabled in the manifest, when the application starts, this starts receiving data
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        TODO("CurrencyValuesReceiver.onReceive() is not implemented")
    }
}