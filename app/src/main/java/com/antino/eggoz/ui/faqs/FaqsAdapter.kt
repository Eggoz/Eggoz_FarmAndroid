package com.antino.eggoz.ui.faqs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import java.util.ArrayList

class FaqsAdapter(
    private val title: ArrayList<String>,
    private val des: ArrayList<String>
) : RecyclerView.Adapter<FaqsAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_faqs_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txttitle.text = title[position]
        holder.txtdes.text = des[position]
        holder.item_parent.setOnClickListener {
            if (holder.txtdes.isVisible)
                holder.txtdes.visibility = View.GONE
            else
                holder.txtdes.visibility = View.VISIBLE
        }
        holder.imgexpand.setOnClickListener {
            if (holder.txtdes.isVisible)
                holder.txtdes.visibility = View.GONE
            else
                holder.txtdes.visibility = View.VISIBLE
        }


    }

    override fun getItemCount(): Int {
        return title.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttitle: TextView = itemView.findViewById(R.id.item_faqs_title)
        val txtdes: TextView = itemView.findViewById(R.id.item_faqs_title_des)
        val imgexpand: ImageView = itemView.findViewById(R.id.img_expand)
        val item_parent: RelativeLayout = itemView.findViewById(R.id.item_faqs_relative_layout)


    }


}