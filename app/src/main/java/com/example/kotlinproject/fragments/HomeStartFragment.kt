package com.example.kotlinproject.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.kotlinproject.R
import com.example.kotlinproject.activities.AddRegActivity
import com.example.kotlinproject.services.CryptoValuesService

class HomeStartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home_start, container, false)
        val receiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                //val btcPrice = intent?.getDoubleExtra(WeatherService.EXTRA_TEMP, -200.0) ?: -300.0
                //if (btcPrice < 0) {
                //    Log.d("LifeCycle", "Error")
                //} else {
                //    Log.d("LifeCycle", btcPrice.toString())
                //}
            }
        }

        // @todo Recycler mas adelante
        val buttonAddReg = rootView.findViewById<Button>(R.id.addRegButton)
        buttonAddReg.setOnClickListener {
            val ciudad = "moscu"

            // Comienza un servicio
            val intent = Intent(activity, CryptoValuesService::class.java)
            intent.putExtra("ciudad", ciudad)
            activity?.startService(intent)
            Log.d("Temperatura", "Pase por aqui")
            //goToAddReg()
        }

        return rootView
    }

    private fun goToAddReg() {
        val intent = Intent(activity, AddRegActivity::class.java) //context se debe sacar de la activity contenedora
        startActivity(intent)
    }
}