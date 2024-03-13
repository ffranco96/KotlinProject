package com.example.kotlinproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.kotlinproject.R

class AddRegFragmentDetail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_reg_detail, container, false)

        // Buttons
        val confirmButton = rootView.findViewById<ImageButton>(R.id.confirmButton)
        val backButton = rootView.findViewById<ImageButton>(R.id.backButton)
        confirmButton.setOnClickListener {
            requireActivity().finish()
        }
        backButton.setOnClickListener {
            requireActivity().finish()
        }
        return rootView
    }
}