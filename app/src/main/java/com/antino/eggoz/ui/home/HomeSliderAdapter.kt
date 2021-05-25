package com.antino.eggoz.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils.substring
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.antino.eggoz.ui.home.model.NeccRate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class HomeSliderAdapter(var mcontext:HomeFragment,
    var results: List<NeccRate.Result>? = null
)  : RecyclerView.Adapter<HomeSliderAdapter.ViewHolder>( ) {
    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
         listItem=
            layoutInflater.inflate(R.layout.item_home_slider1_adapter_view, parent, false)
        context=listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        if (results?.size==null)
            return 0
        else
            return 1
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dddate="${results!![position].modifiedAt}"

       dddate= dddate.substring(0,dddate.length-10)
        val format = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US
        )
        format.setTimeZone(TimeZone.getTimeZone("UTC"))
        try {
            val date: Date = format.parse("${results!![position].modifiedAt}")
            val sdf=SimpleDateFormat("dd MMM yyyy hh:mm aa")
            dddate=sdf.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("data","Exception ${e.message}")
        }


        holder.txttype.text= "White Egg \n(${dddate})"

        holder.txtneccprice.text="Rs.${String.format("%.2f",results!![position].currentRate.toDouble().roundToInt()/100.0)}"
        holder.neccnamezone.text="NECC(${results!![position].neccCity.name})"

        Glide.with(context)
            .asBitmap()
            .load("https://eggoz-android.s3.ap-south-1.amazonaws.com/AndroidDummy/MicrosoftTeams-image+(5).png")
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.imgbackground)

        listItem.setOnClickListener { mcontext.loadall() }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttype: TextView = itemView.findViewById(R.id.txt_item_home_slider_title1)
        val neccnamezone: TextView = itemView.findViewById(R.id.txt_item_home_slider_title4)
        val txtneccprice: TextView = itemView.findViewById(R.id.txt_item_home_slider_title5)
        val imgbackground: ImageView = itemView.findViewById(R.id.banner_image)



    }

}
