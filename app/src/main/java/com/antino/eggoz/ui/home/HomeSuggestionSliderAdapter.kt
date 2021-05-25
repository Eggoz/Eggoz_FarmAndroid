package com.antino.eggoz.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.home.model.WordpressFeed
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class HomeSuggestionSliderAdapter(
    var mcontext: MainActivity,
    var results: List<WordpressFeed.Resultss.Result>? = null
) : RecyclerView.Adapter<HomeSuggestionSliderAdapter.ViewHolder>() {
    private lateinit var context: Context
    lateinit var listItem: View


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(
                R.layout.item_home_suggestion_slider2_adapter_view,
                parent,
                false
            )
        context = listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return results?.size!!
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txttitle.text = results!![position].title

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.txtdes.text =
                Html.fromHtml(results!![position].description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.txtdes.text = Html.fromHtml(results!![position].description)
        }

        listItem.setOnClickListener {
            mcontext.loadWebview(results!![position].link)
        }
        Glide.with(mcontext)
            .asBitmap()
            .load(results!![position].image_url)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.imgtop)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttitle: TextView = itemView.findViewById(R.id.txt_item_home_suggestion_slider_title1)
        val txtdes: TextView = itemView.findViewById(R.id.txt_item_home_suggestion_slider_title2)
        val imgtop: ImageView = itemView.findViewById(R.id.banner_image)


    }

}
