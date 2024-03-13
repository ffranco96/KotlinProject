package com.example.kotlinproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.kotlinproject.R

class AddRegFragmentAmount : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_reg_amount, container, false)

        // Spinner
        val currencies = listOf("ARS", "USD", "USDT", "BTC")
        val currencySpinner = rootView.findViewById<Spinner>(R.id.currencySpinner)
        val spinnerAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item,currencies)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        currencySpinner.adapter = spinnerAdapter

        // Buttons
        val nextButton = rootView.findViewById<ImageButton>(R.id.nextButton)
        val cancelButton = rootView.findViewById<ImageButton>(R.id.cancelButton)
        nextButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_addRegFragmentAmount_to_addRegFragmentDetail)
            //findNavController().navigate(R.id.action_addRegFragmentAmount_to_addRegFragmentDetail)
        )
        cancelButton.setOnClickListener {
            requireActivity()?.finish()
        }

        return rootView
    }
}