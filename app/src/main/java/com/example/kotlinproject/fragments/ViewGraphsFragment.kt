package com.example.kotlinproject.fragments

import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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

        return rootView
    }

    override fun onResume() {
        //activity?.registerReceiver(receiver, intentFilter)
        super.onResume()
        val provider = RecordsProvider.getProvider()
        var db: AppDataBase = AppDataBase.getInstance()
        balancesByCategory = db.balancesDao().getAll().toMutableList()

        // TOOD dibujar graficos en base a los datos
    }
}