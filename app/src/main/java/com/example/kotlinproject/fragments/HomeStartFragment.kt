package com.example.kotlinproject.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.kotlinproject.R
import com.example.kotlinproject.activities.AddRegActivity
import com.example.kotlinproject.services.CryptoValuesService

class HomeStartFragment : Fragment() {
    val textBtcValue: TextView? = null

    val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val btcValue = intent?.getDoubleExtra(CryptoValuesService.EXTRA_TEMP, -200.0) ?: -300.0 // @todo modify and replace for btc
            Log.d("Temperatura", "Valor recibido $btcValue")
            //Toast.makeText(context, "Llego valor btc: $btcValue",Toast.LENGTH_LONG).show()
            if (btcValue < 0) {
                textBtcValue?.text = "0.00"
                Log.d("LifeCycle", "Error")
            } else {
                textBtcValue?.text = btcValue.toString()
                Log.d("LifeCycle", btcValue.toString())
            }
        }
    }
    override fun onResume() {
        val intentFilter = IntentFilter(CryptoValuesService.ACTION_TEMP)
        activity?.registerReceiver(receiver, intentFilter)
        super.onResume()
    }

    override fun onPause() {
        activity?.unregisterReceiver(receiver)
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home_start, container, false)
        textBtcValue?.text = rootView.findViewById(R.id.textBtcValue)

        // @todo Recycler mas adelante
        val buttonAddReg = rootView.findViewById<Button>(R.id.addRegButton)
        buttonAddReg.setOnClickListener {
            goToAddReg()
        }

        val buttonUpdValues = rootView.findViewById<Button>(R.id.buttonUpdValues)
        buttonUpdValues.setOnClickListener {
            val ciudad = "buenosaires"
            textBtcValue?.text = "..."

            // Comienza un servicio
            val intent = Intent(activity, CryptoValuesService::class.java)
            intent.putExtra("ciudad", ciudad)
            activity?.startService(intent)
        }

        return rootView
    }

    private fun goToAddReg() {
        val intent = Intent(activity, AddRegActivity::class.java) //context se debe sacar de la activity contenedora
        startActivity(intent)
    }
}