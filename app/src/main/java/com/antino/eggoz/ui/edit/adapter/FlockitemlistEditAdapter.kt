package com.antino.eggoz.ui.edit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.antino.eggoz.ui.edit.EditShedFragment

class FlockitemlistEditAdapter(val callbacks: EditShedFragment,
                               private val flockName:ArrayList<String>,
                               private val flockbreed:ArrayList<String>,
                               private val flockage:ArrayList<String>,
                               private val flockquantity:ArrayList<String>,
                               private val eggtype:ArrayList<String>,
                               private val flockbreedid:ArrayList<Int>
) : RecyclerView.Adapter<FlockitemlistEditAdapter.ViewHolder>() {
    lateinit var listItem: View
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem= layoutInflater.inflate(R.layout.item_flock, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txt_flockName.text = flockName[position]
        holder.txt_flockbreed.text = flockbreed[position]
        holder.txt_flockage.text = flockage[position]
        holder.txt_flockquantity.text = flockquantity[position]
        if (eggtype[position]==""){
            holder.txt_eggtype.visibility= View.GONE
            holder.eggtype_title.visibility= View.GONE
        }else
            holder.txt_eggtype.text = eggtype[position]

        holder.img_edit.setOnClickListener {
            callbacks.updateposion(position)
        }
    }

    override fun getItemCount(): Int {
        return flockquantity.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_flockName: TextView = itemView.findViewById(R.id.txt_flockName)
        val txt_flockbreed: TextView = itemView.findViewById(R.id.txt_flockbreed)
        val txt_flockage: TextView = itemView.findViewById(R.id.txt_flockage)
        val txt_flockquantity: TextView = itemView.findViewById(R.id.txt_flockquantity)
        val txt_eggtype: TextView = itemView.findViewById(R.id.txt_eggtype)

        val img_edit: ImageView = itemView.findViewById(R.id.img_edit)

        val eggtype_title: TextView = itemView.findViewById(R.id.textView17)


    }

}