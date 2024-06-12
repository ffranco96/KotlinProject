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
import com.example.kotlinproject.activities.ViewGraphsActivity
import com.example.kotlinproject.adapters.RecordsAdapter
import com.example.kotlinproject.model.Balance
import com.example.kotlinproject.model.Category
import com.example.kotlinproject.model.Converters
import com.example.kotlinproject.model.Rec
import com.example.kotlinproject.model.RecordsProvider
import com.example.kotlinproject.services.CryptoValuesService
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader

class HomeStartFragment : Fragment() {
    var textBtcValue: TextView? = null
    var textFirstCurrencyBalance: TextView? = null
    var buttonViewGraphs: Button? = null

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
        val provider = RecordsProvider.getProvider() // TODO podria haber ido en el activity principal

        // Update amount on home screen
        if(provider.updateTotalBalance() == App.RET_FALSE){
            textFirstCurrencyBalance?.text = 0.0.toString()
        } else {
            val obtainedTotalsReg = provider.getDb().balancesDao().getTotalBalanceRec()
            textFirstCurrencyBalance?.text = obtainedTotalsReg.amount.toString()
        }

        // Update state of graphs button
        if(provider.getDb().balancesDao().countBalances() < 2) {
            buttonViewGraphs?.isClickable = false
            buttonViewGraphs?.isEnabled = false
            buttonViewGraphs?.setBackgroundColor(requireContext().getColor(R.color.sad_grey))
        } else {
            buttonViewGraphs?.isClickable = true
            buttonViewGraphs?.isEnabled = true
            buttonViewGraphs?.setBackgroundColor(requireContext().getColor(R.color.ui_blue))
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
        val provider = RecordsProvider.getProvider()

        textBtcValue = rootView.findViewById(R.id.textBtcValue)
        textFirstCurrencyBalance = rootView.findViewById(R.id.textViewFirstCurrencyBalance)

        val buttonAddReg = rootView.findViewById<Button>(R.id.addRegButton)
        buttonAddReg.setOnClickListener {
            goToAddReg()
        }

        val buttonUpdValues = rootView.findViewById<Button>(R.id.buttonUpdValues)
        buttonUpdValues.setOnClickListener {
            textBtcValue?.text = "\uD83D\uDD52"

            // Comienza un servicio
            val intent = Intent(activity, CryptoValuesService::class.java)
            activity?.startService(intent)
        }

        val buttonSynchronize = rootView.findViewById<Button>(R.id.buttonSynchronize)
        buttonSynchronize.setOnClickListener {
            var recordsList = mutableListOf<Rec>()
            val db = provider?.getDb()
            recordsList = readCSVFile(provider)
            recordsList.forEach{
                provider?.addRecord(it)
            }

            if(db?.balancesDao()?.countBalances() == 0) {
                Log.d("Debugger", "Initialize balances table")
                db.balancesDao().insertIntoBalances(Balance(App.context.getString(R.string.text_db_tag_totals), 0, 0.0))
            }

            recordsList.forEach{
                provider?.updateCategoryBalance(it)
            }
            provider.updateTotalBalance()

            // Update amount on home screen
            if(provider.updateTotalBalance() == App.RET_FALSE){
                textFirstCurrencyBalance?.text = 0.0.toString()
            } else {
                val obtainedTotalsReg = provider.getDb().balancesDao().getTotalBalanceRec()
                textFirstCurrencyBalance?.text = obtainedTotalsReg.amount.toString()
            }

            // Update state of graphs button
            if(provider.getDb().balancesDao().countBalances() < 2) {
                buttonViewGraphs?.isClickable = false
                buttonViewGraphs?.isEnabled = false
                buttonViewGraphs?.setBackgroundColor(requireContext().getColor(R.color.sad_grey))
            } else {
                buttonViewGraphs?.isClickable = true
                buttonViewGraphs?.isEnabled = true
                buttonViewGraphs?.setBackgroundColor(requireContext().getColor(R.color.ui_blue))
            }
        }

        buttonViewGraphs = rootView.findViewById<Button>(R.id.buttonViewGraphs)
        buttonViewGraphs?.setOnClickListener {
            goToViewGraphs()
        }

        // Configurar RecyclerView
        val recyclerRegs = rootView.findViewById<RecyclerView>(R.id.recyclerRegs)
        recyclerRegs.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL,
            true
        )
        val adapter = RecordsAdapter()
        recyclerRegs.adapter = adapter

        provider.registerListener {
            adapter.notifyDataSetChanged()
        }

        return rootView
    }

    private fun readCSVFile(provider: RecordsProvider):MutableList<Rec> {

        val bufferReader = BufferedReader(requireActivity().assets.open("records.csv").reader())
        val csvParser = CSVParser.parse(bufferReader, CSVFormat.DEFAULT ) // Note: can use .withIgnoreHeaderCase().withTrim().withRecordSeparator(","))
        val recordsList = mutableListOf<Rec>()
        csvParser.forEach {
            it?.let {
                val newRec = Rec(
                    amount = it.get(0).toDouble(),
                    title = it.get(1),
                    description = it.get(2),
                    category = provider.convertToCategory(it.get(3)),
                    date = it.get(4),
                    currency = it.get(5),
                )
                recordsList.add(newRec)
            }
        }
        return recordsList
    }

    private fun goToViewGraphs() {
        val intent = Intent(activity, ViewGraphsActivity::class.java)
        startActivity(intent)
    }

    private fun goToAddReg() {
        val intent = Intent(activity, AddRegActivity::class.java) //context se debe sacar de la activity contenedora
        startActivity(intent)
    }
}