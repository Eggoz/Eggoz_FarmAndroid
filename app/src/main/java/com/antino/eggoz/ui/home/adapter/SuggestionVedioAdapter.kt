package com.antino.eggoz.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.home.model.VideoList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class SuggestionVedioAdapter(private val mcontext: MainActivity,var results: List<VideoList.Result>? = null
)  : RecyclerView.Adapter<SuggestionVedioAdapter.ViewHolder>( ) {
    private lateinit var context: Context
    private lateinit var listItem: View

    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_home_suggestion_vedio_adapter_view, parent, false)
        context=listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return results?.size!!
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txt_des.text=results!![position].description

        Glide.with(context)
            .asBitmap()
            .load(results!![position].imageUrl)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.imageView)


        listItem.setOnClickListener {
            mcontext.HomeCallbackexoplayer(results!![position].videoUrl,results!![position].id)
        }


    }




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView:ImageView =itemView.findViewById(R.id.img_vedio)
        val txt_des:TextView =itemView.findViewById(R.id.txt_des)


    }

}
