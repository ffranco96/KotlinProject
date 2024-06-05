package com.example.kotlinproject.fragments

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.kotlinproject.App
import com.example.kotlinproject.R
import com.example.kotlinproject.model.AppDataBase
import com.example.kotlinproject.model.Balance
import com.example.kotlinproject.model.RecordsProvider
import com.example.kotlinproject.services.CryptoValuesService

class ViewGraphsFragment : Fragment() {
    private var balancesByCategory = mutableListOf<Balance>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun updateBalancesTextViews(rootView:View):Unit{
        val db = RecordsProvider.getProvider().getDb()
        if(rootView == null)
            return

        if(db.balancesDao().countBalances() > 0){
            val numericValue1 = rootView.findViewById<TextView>(R.id.numericValue1)
            val numericValue2 = rootView.findViewById<TextView>(R.id.numericValue2)
            val numericValue3 = rootView.findViewById<TextView>(R.id.numericValue3)

            balancesByCategory = db.balancesDao().getAll().toMutableList()
            if( balancesByCategory.size > 1) { // At least totals balance and any other
                try {
                    numericValue1.text = balancesByCategory[1].amount.toString()
                    numericValue2.text = balancesByCategory[2].amount.toString()//TODO avoid exception
                    numericValue3.text = balancesByCategory[3].amount.toString()
                }catch(exception: ArrayIndexOutOfBoundsException){
                    Log.d("Debugger", "Se accedió a valor fuera del string")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_view_graphs, container, false)
        val cancelButton = rootView.findViewById<ImageButton>(R.id.cancelButton2)
        cancelButton.setOnClickListener {
            requireActivity().finish()
        }
        updateBalancesTextViews(rootView)

        return rootView
    }

    override fun onResume() {
        //activity?.registerReceiver(receiver, intentFilter)
        super.onResume()
        val provider = RecordsProvider.getProvider()
        var db: AppDataBase = AppDataBase.getInstance()
        //balancesByCategory = db.balancesDao().getAll().toMutableList()
        //updateBalancesTextViews(rootView) TODO ver donde meter el rootView o como actualizar los textviews desde ambas funciones

        // TOOD dibujar graficos en base a los datos
    }
}