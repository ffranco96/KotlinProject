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
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView

    fun updateBalancesTextViews(provider: RecordsProvider):Unit{
        if(provider == null)
            return
        val db = provider.getDb()
        if(db.balancesDao().countBalances() > 0){
            balancesByCategory = db.balancesDao().getAll().toMutableList()
            if( balancesByCategory.size > 1) { // At least totals balance and any other
                try {
                    textView1.text = balancesByCategory[1].amount.toString()
                    textView2.text = balancesByCategory[2].amount.toString()//TODO avoid exception
                    textView3.text = balancesByCategory[3].amount.toString()
                }catch(exception: ArrayIndexOutOfBoundsException){
                    Log.d("Debugger", "Se accedió a valor fuera del string")
                }
            }
        }
        return
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_view_graphs, container, false)
        val cancelButton = rootView.findViewById<ImageButton>(R.id.cancelButton2)
        val provider = RecordsProvider.getProvider()
        textView1 = rootView.findViewById<TextView>(R.id.numericValue1)
        textView2 = rootView.findViewById<TextView>(R.id.numericValue2)
        textView3 = rootView.findViewById<TextView>(R.id.numericValue3)
        cancelButton.setOnClickListener {
            requireActivity().finish()
        }
        updateBalancesTextViews(provider)

        return rootView
    }

    override fun onResume() {
        //activity?.registerReceiver(receiver, intentFilter)
        super.onResume()
        val provider = RecordsProvider.getProvider()
        updateBalancesTextViews(provider)

        // TOOD dibujar graficos en base a los datos
    }
}