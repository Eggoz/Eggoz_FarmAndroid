package com.antino.eggoz.ui.profile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.edit.EditProfileFragment

class ErrorMessage(var message: String, var mcontext: EditProfileFragment, var stat:Boolean) : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val vew: View = inflater.inflate(R.layout.item_message, container, false)
        val text: TextView = vew.findViewById(R.id.message)
        val btn_close: CardView = vew.findViewById(R.id.btn_Submit)
        val img_check: ImageView =vew.findViewById(R.id.img_check)


        if (!stat)
            img_check.setImageResource(R.drawable.logo1)

        text.text = message
        btn_close.setOnClickListener {
            mcontext.save()
            dismiss()
        }
   /*     dialog!!.setCancelable(false)

        dialog!!.setCanceledOnTouchOutside(false)

*/


        return vew
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}