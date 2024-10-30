package com.example.kotlinproject.fragments

import android.app.DatePickerDialog
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
import android.widget.TextView
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
        OTHER,
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
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateSelector = rootView.findViewById<TextView>(R.id.datePicker)
        dateSelector.text = dateFormat.format(calendar.time)

        var selectedYear = calendar.get(Calendar.YEAR)
        var selectedMonth = calendar.get(Calendar.MONTH)
        var selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        dateSelector.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    selectedYear = year
                    selectedMonth = month
                    selectedDayOfMonth = dayOfMonth

                    var selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
                    dateSelector.text = dateFormat.format(selectedCalendar.time)
                },
                selectedYear,
                selectedMonth,
                selectedDayOfMonth
            )

            datePickerDialog.show()
        }

        /*calendar.add(Calendar.DAY_OF_MONTH, -1)
        dateString = dateFormat.format(Date(calendar.timeInMillis))
        dateStringList.add(dateString)

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        dateString = dateFormat.format(Date(calendar.timeInMillis))
        dateStringList.add(dateString)

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        dateString = dateFormat.format(Date(calendar.timeInMillis))
        dateStringList.add(dateString)*/

        /*val dateSpinnerAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, dateStringList)
        dateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        dateSpinner.adapter = dateSpinnerAdapter*/

        val activityContext = (activity as AddRegActivity)

        confirmButton.setOnClickListener {
            if(activityContext.newRecord.amount != 0.0) {
                activityContext.newRecord.title = titleEditText.text.toString()
                activityContext.newRecord.description = descriptionEditText.text.toString()
                activityContext.newRecord.date = dateSelector.text.toString()
                activityContext.newRecord.category =
                    provider.convertToCategory(categoriesSpinner.selectedItem.toString())

                provider.addRecord(activityContext.newRecord)
                Log.d("Debugger", activityContext.newRecord.toString())
            }
            requireActivity().finish()
        }
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return rootView
    }
}