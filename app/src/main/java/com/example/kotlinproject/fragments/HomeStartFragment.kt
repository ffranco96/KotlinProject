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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.App
import com.example.kotlinproject.R
import com.example.kotlinproject.activities.AddRegActivity
import com.example.kotlinproject.adapters.RecordsAdapter
import com.example.kotlinproject.model.RecordsProvider
import com.example.kotlinproject.services.CryptoValuesService

class HomeStartFragment : Fragment() {
    var textBtcValue: TextView? = null
    var textFirstCurrencyBalance: TextView? = null

    //@todo en esta screen se puede agregar un selector de moneda que, de paso, active un intent para mostrar su precio
    val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("BTC_Value","Onreceive started")
            val btcValue = intent?.getDoubleExtra(CryptoValuesService.EXTRA_CRYPTO_VALUES, -200.0) ?: -300.0 // @todo modify and replace for btc
            Log.d("BTC_Value", "Valor recibido $btcValue")
            //Toast.makeText(context, "Llego valor btc: $btcValue",Toast.LENGTH_LONG).show()
            if (btcValue < 0) {
                textBtcValue?.text = "0.00"
                Log.d("BTC_Value", "Error")
            } else {
                textBtcValue?.text = String.format("%.2f",btcValue)
                Log.d("BTC_Value", btcValue.toString())
            }
        }
    }
    override fun onResume() {
        val intentFilter = IntentFilter(CryptoValuesService.ACTION_CRYPTO_VALUES)
        activity?.registerReceiver(receiver, intentFilter)
        super.onResume()
        val provider = RecordsProvider.getProvider()
        if(provider.updateTotalBalance() == App.RET_FALSE){
            textFirstCurrencyBalance?.text = 0.0.toString()
        } else {
            val obtainedTotalsReg = provider.getDb().balancesDao().getTotalBalanceRec()
            textFirstCurrencyBalance?.text = obtainedTotalsReg.amount.toString()
        }
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
        textBtcValue = rootView.findViewById(R.id.textBtcValue)
        textFirstCurrencyBalance = rootView.findViewById(R.id.textViewFirstCurrencyBalance)

        val buttonAddReg = rootView.findViewById<Button>(R.id.addRegButton)
        buttonAddReg.setOnClickListener {
            goToAddReg()
        }

        val buttonUpdValues = rootView.findViewById<Button>(R.id.buttonUpdValues)
        buttonUpdValues.setOnClickListener {
            textBtcValue?.text = "\uD83D\uDD52"
            Log.d("BTC_Value","Paso por aca")

            // Comienza un servicio
            val intent = Intent(activity, CryptoValuesService::class.java)
            activity?.startService(intent)
        }

        // Configurar RecyclerView
        val recyclerRegs = rootView.findViewById<RecyclerView>(R.id.recyclerRegs)
        recyclerRegs.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL,
            false
        )
        val adapter = RecordsAdapter()
        recyclerRegs.adapter = adapter

        RecordsProvider.getProvider().getRecordsList().forEach {
            Log.d("Registro",it.toString())
        }

        RecordsProvider.getProvider().registerListener {
            adapter.notifyDataSetChanged()
        }

        return rootView
    }

    private fun goToAddReg() {
        val intent = Intent(activity, AddRegActivity::class.java) //context se debe sacar de la activity contenedora
        startActivity(intent)
    }
}