package com.example.kotlinproject.fragments

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
import androidx.navigation.fragment.findNavController
import com.example.kotlinproject.R
import com.example.kotlinproject.activities.AddRegActivity
import com.example.kotlinproject.model.RecordsProvider

class AddRegFragmentDetail : Fragment() {
    enum class categoryIds {
        EVENTS_AND_CONCERTS,
        OTHER, //@todo agregar resto de categorias almenos por ahora. Agregarle un id String a la categoria. Agregar un spinner
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_reg_detail, container, false)

        // Buttons
        val confirmButton = rootView.findViewById<ImageButton>(R.id.confirmButton)
        val backButton = rootView.findViewById<ImageButton>(R.id.backButton)
        val titleEditText = rootView.findViewById<EditText>(R.id.titleEditText)
        val descriptionEditText = rootView.findViewById<EditText>(R.id.descriptionEditText)

        val provider = RecordsProvider.getProvider()

        //Spinner
        val categoriesSpinner = rootView.findViewById<Spinner>(R.id.categorySpinner)
        val spinnerAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, provider.getCategoriesIdsList())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        categoriesSpinner.adapter = spinnerAdapter

        val activityContext = (activity as AddRegActivity)

        confirmButton.setOnClickListener {
            activityContext.newRecord.title = titleEditText.text.toString()
            activityContext.newRecord.description = descriptionEditText.text.toString()
            activityContext.newRecord.date = "2024-05-25" //@todo quitar hardcodeo
            when(categoriesSpinner.selectedItem.toString()){
                "Comida y alimentos"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Restaurant y comida rapida"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Ropa"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Vehiculos"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Mantenimiento vehiculos"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Recitales y eventos"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Salud"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Prepaga y obra social"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Medicamentos e insumos"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Hobbies"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Pintura, dibujo y fotografia"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Inversiones y finanzas"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Salario"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                else -> activityContext.newRecord.category = provider.getCategoriesList()[1] //@todo agregar resto de categorias almenos por ahora
            }

            provider.addRecord(activityContext.newRecord)
            Log.d("Data", activityContext.newRecord.toString())
            requireActivity().finish()
        }
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return rootView
    }
}