package com.example.kotlinproject.fragments

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Switch
import androidx.navigation.fragment.findNavController
import com.example.kotlinproject.R
import com.example.kotlinproject.activities.AddRegActivity
import com.example.kotlinproject.services.CryptoValuesService

class AddRegFragmentAmount : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_reg_amount, container, false)

        // Spinner
        val currencies = listOf("ARS") // TODO "USD", "USDT", "BTC" . Por el momento solo ARS
        val currencySpinner = rootView.findViewById<Spinner>(R.id.currencySpinner)
        val spinnerAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item,currencies)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        currencySpinner.adapter = spinnerAdapter

        // Buttons
        val nextButton = rootView.findViewById<ImageButton>(R.id.nextButton)
        val cancelButton = rootView.findViewById<ImageButton>(R.id.cancelButton2)
        val amountEditText = rootView.findViewById<EditText>(R.id.amountEditText)
        val incomeExpensesSwitch = rootView.findViewById<Switch>(R.id.incomeExpensesSwitch)

        val activityContext = (activity as AddRegActivity)
        nextButton.setOnClickListener{//@todo check
            val amountText = amountEditText.text
            if(amountText.isEmpty()){
                activityContext.newRecord.amount = 0.0
            } else {
                activityContext.newRecord.amount = if(!(incomeExpensesSwitch.isChecked))
                                                    amountEditText.text.toString().toDouble() * (-1)
                                                    else amountEditText.text.toString().toDouble()
            }


            activityContext.newRecord.currency = currencySpinner.selectedItem.toString()
            Log.d("LifeCycle", "Presionado nextbutton")
            findNavController().navigate(R.id.action_addRegFragmentAmount_to_addRegFragmentDetail)
        }

        cancelButton.setOnClickListener {
            requireActivity().finish()
        }

        return rootView
    }
}