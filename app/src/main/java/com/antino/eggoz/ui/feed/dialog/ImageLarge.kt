package com.antino.eggoz.ui.feed.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.antino.eggoz.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageLarge(
    var url:String?,
    var mcontext:Context
): DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val vew: View = inflater.inflate(R.layout.list_img, container, false)
        val image_zome: ImageView = vew.findViewById(R.id.image_zome)
        val btn_close: ImageView = vew.findViewById(R.id.btn_close)

        btn_close.setOnClickListener { dialog?.cancel() }
        Glide.with(mcontext)
            .asBitmap()
            .load(url!!)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(image_zome)



        return vew
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels*0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}