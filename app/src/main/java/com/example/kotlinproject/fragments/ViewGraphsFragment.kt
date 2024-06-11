package com.example.kotlinproject.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.kotlinproject.R
import com.example.kotlinproject.model.Balance
import com.example.kotlinproject.model.RecordsProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ViewGraphsFragment : Fragment() {
    private var balancesByCategory = mutableListOf<Balance>()
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private var barArrayList = ArrayList<BarEntry>()

    //fun updateBalancesTextViews(provider: RecordsProvider):Unit{
    //    if(provider == null)
    //        return
    //    val db = provider.getDb()
    //    if(db.balancesDao().countBalances() > 0){
    //        balancesByCategory = db.balancesDao().getAll().toMutableList()
    //        if( balancesByCategory.size > 1) { // At least totals balance and any other
    //            try {
    //                textView1.text = balancesByCategory[1].amount.toString()
    //                textView2.text = balancesByCategory[2].amount.toString()//TODO avoid exception
    //                textView3.text = balancesByCategory[3].amount.toString()
    //            }catch(exception: ArrayIndexOutOfBoundsException){
    //                Log.d("Debugger", "Se accedió a valor fuera del string")
    //            }
    //        }
    //    }
    //    return
    //}

    fun updateBalancesBarChart(provider: RecordsProvider):Unit{
        if(provider == null)
            return
        val db = provider.getDb()
        if(db.balancesDao().countBalances() > 0){
            balancesByCategory = db.balancesDao().getAll().toMutableList()
            if( balancesByCategory.size > 1) { // At least totals balance and any other
                var i = 2f
                balancesByCategory.forEach{
                    barArrayList.add(BarEntry(i,it.qtty.toFloat()))
                    //barArrayList.add(BarEntry(i,30f))
                    i++
                }
            }
        }
        return
    }

    private fun getData(){
        barArrayList.add(BarEntry(2f, 10f))
        barArrayList.add(BarEntry(3f, 10f))
        barArrayList.add(BarEntry(4f, 10f))
        barArrayList.add(BarEntry(5f, 10f))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_view_graphs, container, false)
        val cancelButton = rootView.findViewById<ImageButton>(R.id.cancelButton2)
        val provider = RecordsProvider.getProvider()
        cancelButton.setOnClickListener {
            requireActivity().finish()
        }

        val barChart = rootView.findViewById<BarChart>(R.id.barchart)
        updateBalancesBarChart(provider)
        val barDataSet = BarDataSet(barArrayList, "Estadisticas")
        val barData = BarData(barDataSet)
        barChart.data = barData
        // Color bar data set
        barDataSet.colors = (ColorTemplate.COLORFUL_COLORS).toMutableList()
        // Text color
        barDataSet.valueTextColor = Color.BLACK
        // Text size
        barDataSet.valueTextSize = 16f
        barChart.description.isEnabled = true


        return rootView
    }

    override fun onResume() {
        //activity?.registerReceiver(receiver, intentFilter)
        super.onResume()
        val provider = RecordsProvider.getProvider()
        //updateBalancesTextViews(provider)

    }
}