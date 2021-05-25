package com.antino.eggoz.ui.sell_shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.antino.eggoz.R
import com.antino.eggoz.ui.sell_shop.model.Banner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class SliderAdapter(var mcontext:SellShopFragment,var results: List<Banner.Result>?
) : PagerAdapter() {
    override fun getCount(): Int {
        return results?.size!!
    }

    override fun getPageWidth(position: Int): Float {
        return 0.8f
    }
    fun size():Int{
        return results?.size!!
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageLayout: View =
            LayoutInflater.from(container.context).inflate(R.layout.slider_item_container, container, false)!!

        val imagename = imageLayout
            .findViewById<View>(R.id.txt_name) as TextView
        val imageprice= imageLayout
            .findViewById<View>(R.id.txt_item_price) as TextView
        val image= imageLayout
            .findViewById<View>(R.id.img_item) as ImageView

        /*imagename.text=item_name[position]
        imageprice.text="Rs. "+item_price[position]*/

        Glide.with(mcontext)
            .asBitmap()
            .load(results!![position].image)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(image)

        container.addView(imageLayout)


        return imageLayout
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


}