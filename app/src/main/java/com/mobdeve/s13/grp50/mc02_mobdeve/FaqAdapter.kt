package com.mobdeve.s13.grp50.mc02_mobdeve

import FaqItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FaqAdapter(private val faqList: List<FaqItem>) : RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    // Create the ViewHolder class that will bind the data to the views
    class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtQuestion: TextView = itemView.findViewById(R.id.txtQuestion)
        val txtAnswer: TextView = itemView.findViewById(R.id.txtAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        // Inflate the faq.xml layout for each item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.faq, parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        // Bind data to the views
        val faqItem = faqList[position]
        holder.txtQuestion.text = faqItem.question
        holder.txtAnswer.text = faqItem.answer
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
}
