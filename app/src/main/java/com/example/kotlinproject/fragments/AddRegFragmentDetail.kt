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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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

        // Category spinner
        val categoriesSpinner = rootView.findViewById<Spinner>(R.id.categorySpinner)
        val categSpinnerAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, provider.getCategoriesIdsList())
        categSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        categoriesSpinner.adapter = categSpinnerAdapter

        // Date spinner
        val dateSpinner = rootView.findViewById<Spinner>(R.id.dateSpinner)
        val calendar = Calendar.getInstance()
        val dateStringList = ArrayList<String>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var dateString = dateFormat.format(Date(calendar.timeInMillis))

        calendar.add(Calendar.DAY_OF_MONTH, -1)
        dateString = dateFormat.format(Date(calendar.timeInMillis))
        dateStringList.add(dateString)

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        dateString = dateFormat.format(Date(calendar.timeInMillis))
        dateStringList.add(dateString)

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        dateString = dateFormat.format(Date(calendar.timeInMillis))
        dateStringList.add(dateString)

        val dateSpinnerAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, dateStringList)
        dateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        dateSpinner.adapter = dateSpinnerAdapter

        val activityContext = (activity as AddRegActivity)

        confirmButton.setOnClickListener {
            activityContext.newRecord.title = titleEditText.text.toString()
            activityContext.newRecord.description = descriptionEditText.text.toString()
            activityContext.newRecord.date = dateSpinner.selectedItem.toString()
            when(categoriesSpinner.selectedItem.toString()){// TODO replace by convertToCategory
                "Comida y alimentos"-> activityContext.newRecord.category = provider.getCategoriesList()[0]
                "Restaurant y comida rapida"-> activityContext.newRecord.category = provider.getCategoriesList()[1]
                "Ropa"-> activityContext.newRecord.category = provider.getCategoriesList()[2]
                "Vehiculos"-> activityContext.newRecord.category = provider.getCategoriesList()[3]
                "Mantenimiento vehiculos"-> activityContext.newRecord.category = provider.getCategoriesList()[4]
                "Recitales y eventos"-> activityContext.newRecord.category = provider.getCategoriesList()[5]
                "Salud"-> activityContext.newRecord.category = provider.getCategoriesList()[6]
                "Estudios particulares"-> activityContext.newRecord.category = provider.getCategoriesList()[7]
                "Medicamentos e insumos"-> activityContext.newRecord.category = provider.getCategoriesList()[8]
                "Hobbies"-> activityContext.newRecord.category = provider.getCategoriesList()[9]
                "Pintura, dibujo y fotografia"-> activityContext.newRecord.category = provider.getCategoriesList()[10]
                "Inversiones y finanzas"-> activityContext.newRecord.category = provider.getCategoriesList()[11]
                "Salario"-> activityContext.newRecord.category = provider.getCategoriesList()[12]
                else -> activityContext.newRecord.category = provider.getCategoriesList()[13]
            }

            provider.addRecord(activityContext.newRecord)
            Log.d("Debugger", activityContext.newRecord.toString())
            requireActivity().finish()
        }
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return rootView
    }
}