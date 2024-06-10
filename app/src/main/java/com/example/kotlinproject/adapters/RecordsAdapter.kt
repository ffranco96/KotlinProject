package com.example.kotlinproject.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.App
import com.example.kotlinproject.R
import com.example.kotlinproject.model.Rec
import com.example.kotlinproject.model.RecordsProvider

class RecordsAdapter: RecyclerView.Adapter<RecordsAdapter.RecordViewHolder>() {
    class RecordViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) // Inner class.
    {
        private lateinit var recAmount: TextView
        fun bind(record: Rec){
            val recDescription = itemView.findViewById<TextView>(R.id.description)
            recAmount = itemView.findViewById<TextView>(R.id.amount)
            val recDate = itemView.findViewById<TextView>(R.id.date)
            val recTitle = itemView.findViewById<TextView>(R.id.regTitle)
            val recCategoryIcon = itemView.findViewById<ImageView>(R.id.categoryIcon)

            recDescription.text = record.description
            recAmount.text = record.amount.toString()
            recDate.text = record.date
            recTitle.text = record.title

            val context = itemView.context
            val categoryId = record.category.iconRsc
            val drawable = ContextCompat.getDrawable(context, categoryId)
            recCategoryIcon.setImageDrawable(drawable)
        }

        fun getRecAmount():TextView{
            return recAmount
        }
    }

    // Bind data from a viewHolder with one (and just one) position
    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = RecordsProvider.getProvider().getRecord(position)

        record?.let{
            holder.bind(record)

            // Set color to amount
            val amount = record.amount // Assuming 'amount' is a property in the 'Record' class
            if(amount > 0.0) {
                holder.getRecAmount()?.setTextColor(ContextCompat.getColor(App.context, R.color.positive_green))
            } else {
                holder.getRecAmount()?.setTextColor(ContextCompat.getColor(App.context, R.color.negative_red))
            }
        }
    }

    // Recyclerview asks for an empty viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val inflater =  LayoutInflater.from(parent.context) // get parent.context from param
        val itemRecord = inflater.inflate(R.layout.item_record, parent, false) // attachToRoot always false
        return RecordViewHolder(itemRecord)
    }

    override fun getItemCount(): Int {
        return RecordsProvider.getProvider().getRecordsList().count()
    }

}
