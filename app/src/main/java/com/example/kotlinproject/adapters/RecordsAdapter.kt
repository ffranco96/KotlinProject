package com.example.kotlinproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.R
import com.example.kotlinproject.model.Rec
import com.example.kotlinproject.model.RecordsProvider

class RecordsAdapter: RecyclerView.Adapter<RecordsAdapter.RecordViewHolder>() {
    class RecordViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) // Inner class.
    {
        fun bind(record: Rec){
            val recDescription = itemView.findViewById<TextView>(R.id.description)
            val recAmount = itemView.findViewById<TextView>(R.id.amount)
            val recDate = itemView.findViewById<TextView>(R.id.date)
            val recTitle = itemView.findViewById<TextView>(R.id.regTitle)
            recDescription.text = record.description
            recAmount.text = record.amount.toString()
            recDate.text = record.date
            recTitle.text = record.title
        }
    }

    // Bind data from a viewHolder with one (and just one) position
    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = RecordsProvider.getProvider().get(position)

        record?.let{
            holder.bind(record)
        }
    }

    // Recyclerview asks for an empty viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val inflater =  LayoutInflater.from(parent.context) // get parent.context from param
        val itemRecord = inflater.inflate(R.layout.item_record, parent, false) // attachToRoot always false
        return RecordViewHolder(itemRecord)
    }

    override fun getItemCount(): Int {
        return RecordsProvider.getProvider().listAll().count()
    }

}
