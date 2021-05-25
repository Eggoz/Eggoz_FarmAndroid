package com.antino.eggoz.ui.termscondition

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import java.util.*

class TermsAndConditionAdapter(
    private val title: ArrayList<String>,
    private val des: ArrayList<String>
) : RecyclerView.Adapter<TermsAndConditionAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_term_condition_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txttitle.text = title[position]
        holder.txtdes.text = des[position]



    }

    override fun getItemCount(): Int {
        return title.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttitle: TextView = itemView.findViewById(R.id.term_cond_title)
        val txtdes: TextView = itemView.findViewById(R.id.term_cond_des)



    }




}